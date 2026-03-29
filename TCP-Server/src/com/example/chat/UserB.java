package com.example.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class UserB {

    public static void main(String[] args) throws IOException {
        int port = 5001;

        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            SocketChannel client = null;
            Scanner scanner = new Scanner(System.in);

            while (true) {

                // accept
                if (client == null) {
                    client = server.accept();
                    if (client != null) {
                        client.configureBlocking(false);
                        System.out.println("Client connected");
                    }
                }

                // gửi message
                if (client != null && System.in.available() > 0) {
                    String msg = scanner.nextLine();
                    if (!msg.isEmpty()) {
                        client.write(ByteBuffer.wrap((msg + "\n").getBytes()));
                    }
                }

                // đọc message
                if (client != null) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int read = client.read(buffer);

                    if (read > 0) {
                        buffer.flip();
                        System.out.println("UserA: " +
                                new String(buffer.array(), 0, buffer.limit()));
                    }
                }
            }
        }
    }
}
