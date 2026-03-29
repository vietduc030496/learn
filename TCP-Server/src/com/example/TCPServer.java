package com.example;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    public static void main(String[] args) {
        int port = 3000;

        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(port));

            ExecutorService executorService = Executors.newFixedThreadPool(5);

            System.out.println("Server running on port " + port);

            while (true) {
                SocketChannel clientChannel = serverChannel.accept();

                if (clientChannel == null) {
                    Thread.sleep(50);
                    continue;
                }

                System.out.println("New client: " + clientChannel.getRemoteAddress());

                executorService.submit(() -> {
                    try {
                        handleClient(clientChannel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder sb = new StringBuilder();

        while (true) {
            int bytesRead = socketChannel.read(buffer);

            if (bytesRead == -1) {
                socketChannel.close();
                return;
            }

            if (bytesRead == 0) continue;

            buffer.flip();

            while (buffer.hasRemaining()) {
                char c = (char) buffer.get();
                sb.append(c);

                if (c == '\n') {
                    String message = sb.toString().trim();
                    System.out.println("Received: " + message);

                    sb.setLength(0); // reset buffer

                    String response = "Hello from server\n";
                    ByteBuffer writeBuffer = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));

                    while (writeBuffer.hasRemaining()) {
                        socketChannel.write(writeBuffer);
                    }

                    break;
                }
            }

            buffer.clear();
        }

    }
}
