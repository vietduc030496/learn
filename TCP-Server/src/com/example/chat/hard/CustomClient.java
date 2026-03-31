package com.example.chat.hard;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomClient {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 3000;

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));

        while (true) {
            executorService.submit (() -> {
                Scanner scanner = new Scanner(System.in);
                try {
                    while (true) {
                        String message = scanner.nextLine();
                        socketChannel.write(ByteBuffer.wrap(message.getBytes()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            executorService.submit(() -> {

                try {
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = socketChannel.read(buffer);

                        if (bytesRead == -1) {
                            System.out.println("Server closed connection");
                            break;
                        }

                        buffer.flip();
                        byte[] data = new byte[buffer.remaining()];
                        buffer.get(data, 0, bytesRead);

                        System.out.println("Server: " + new String(data));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }
}
