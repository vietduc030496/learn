package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class TCPServerSelect {

    public static void main(String[] args) throws IOException {
        int port = 4000;

        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        server.configureBlocking(false);

        Selector selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server running on port " + port);

        while (true) {
            selector.select();

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (!key.isValid()) continue;

                try {
                    if (key.isAcceptable()) {
                        handleAccept(key, selector);
                    } else if (key.isReadable()) {
                        handleRead(key);
                    } else if (key.isWritable()) {
                        handleWrite(key);
                    }
                } catch (IOException e) {
                    key.cancel();
                    key.channel().close();
                }
            }
        }
    }

    // ================= ACCEPT =================
    private static void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel client = server.accept();

        if (client == null) return;

        client.configureBlocking(false);

        // mỗi client có buffer riêng
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        client.register(selector, SelectionKey.OP_READ, buffer);

        System.out.println("New client: " + client.getRemoteAddress());
    }

    // ================= READ =================
    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        int bytesRead = client.read(buffer);

        if (bytesRead == -1) {
            System.out.println("Client disconnected");
            client.close();
            key.cancel();
            return;
        }

        buffer.flip();

        StringBuilder sb = new StringBuilder();

        while (buffer.hasRemaining()) {
            char c = (char) buffer.get();
            sb.append(c);

            // newline = end message
            if (c == '\n') {
                String msg = sb.toString().trim();
                System.out.println("Received: " + msg);

                // chuẩn bị response
                String response = "Echo: " + msg + "\n";
                ByteBuffer writeBuffer = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));

                // attach write buffer
                key.attach(writeBuffer);

                // chuyển sang WRITE
                key.interestOps(SelectionKey.OP_WRITE );

                sb.setLength(0);
            }
        }

        buffer.compact(); // 🔥 giữ phần chưa đọc
    }

    // ================= WRITE =================
    private static void handleWrite(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        while (buffer.hasRemaining()) {
            client.write(buffer);
        }

        // sau khi write xong → quay lại READ
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        key.attach(readBuffer);
        key.interestOps(SelectionKey.OP_READ);
    }
}
