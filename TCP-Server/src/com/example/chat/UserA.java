package com.example.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class UserA {

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 5001;

        SocketChannel socketChannel =
                SocketChannel.open(new InetSocketAddress(host, port));

        System.out.println("Connected to server");

        new Thread(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            try {
                while (true) {
                    int read = socketChannel.read(buffer);

                    if (read == -1) {
                        System.out.println("Server closed");
                        socketChannel.close();
                        break;
                    }

                    buffer.flip();

                    StringBuilder sb = new StringBuilder();
                    while (buffer.hasRemaining()) {
                        sb.append((char) buffer.get());
                    }

                    System.out.println("Server: " + sb.toString());

                    buffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Scanner sc = new Scanner(System.in);

        while (true) {
            String message = sc.nextLine();

            socketChannel.write(
                    ByteBuffer.wrap((message + "\n").getBytes(StandardCharsets.UTF_8))
            );
        }
    }
}
