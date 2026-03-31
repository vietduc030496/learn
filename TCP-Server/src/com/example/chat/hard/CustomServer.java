package com.example.chat.hard;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CustomServer {

    private static Selector selector;
    private static final Queue<String> messageQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws IOException {
        int port = 3000;

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);

        selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server running on port " + port);


        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String msg = scanner.nextLine();
                messageQueue.add(msg);
                selector.wakeup();
            }
        }).start();

        while (true) {
            selector.select();

            while (!messageQueue.isEmpty()) {
                String msg = messageQueue.poll();
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

                for (SelectionKey key : selector.keys()) {
                    if (key.channel() instanceof SocketChannel) {
                        SocketChannel client = (SocketChannel) key.channel();
                        key.attach(buffer.duplicate()); // mỗi client 1 buffer
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                }
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (!key.isValid()) continue;

                if (key.isAcceptable()) {
                    handleAccept(key);
                } else if (key.isReadable()) {
                    handleRead(key);
                } else if (key.isWritable()) {
                    handleWrite(key);
                }
            }
        }
    }

    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel client = serverChannel.accept();

        if (client == null) return;

        client.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.register(selector, SelectionKey.OP_READ, buffer);

        System.out.println("New client: " + client.getRemoteAddress());
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        int bytesRead = client.read(buffer);

        if (bytesRead == -1) {
            System.out.println("Client disconnected: " + client.getRemoteAddress());
            key.cancel();
            client.close();
            return;
        }

        if (bytesRead == 0) return;

        buffer.flip();

        byte[] data = new byte[bytesRead];
        buffer.get(data);

        String message = new String(data);
        System.out.println("Client says: " + message);

        buffer.clear();
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        client.write(buffer);

        if (!buffer.hasRemaining()) {
            // gửi xong → quay lại read
            key.interestOps(SelectionKey.OP_READ);
            key.attach(ByteBuffer.allocate(1024));
        }
    }
}
