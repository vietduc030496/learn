package com.example.chat.medium;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class CustomServer {

    public static void main(String[] args) throws IOException {
        int port = 3000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                new Thread(() -> {
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println("Server received: " + line);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

                new Thread(() -> {
                    BufferedWriter writer = null;

                    Scanner scanner = new Scanner(System.in);
                    try {
                        writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                        String message = scanner.nextLine();
                        writer.write(message);
                        writer.newLine();
                        writer.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }).start();
            }
        }
    }
}
