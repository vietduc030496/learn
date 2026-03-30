package com.example.chat.medium;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CustomClient {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 3000;

        try (Socket socket = new Socket(host, port)) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));

                System.out.println("Connected to server");

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

                Scanner sc = new Scanner(System.in);

                while (true) {
                    String msg = sc.nextLine();

                    writer.write(msg);
                    writer.newLine();
                    writer.flush();
                }
            }
        }
    }
}
