package com.example.chat.hard;

import com.example.util.RespUtil;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class RedisLikeClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 3000;

        try (Socket socket = new Socket(host, port)) {

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Connected to Redis-like server");

            // 🔹 Thread đọc response từ server
            new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("Server: " + line);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server");
                }
            }).start();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                // parse command
//                String[] parts = input.split(" ");

                // encode RESP
//                String resp = encodeRESP(parts);

                // gửi server
                writer.write(RespUtil.encode(input));
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
