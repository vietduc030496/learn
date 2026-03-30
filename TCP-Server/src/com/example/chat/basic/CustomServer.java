package com.example.chat.basic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CustomServer {

    public static void main(String[] args) throws IOException {
        int port = 3000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Server received: " + line);

                    writer.write("Received: " + line);
                    writer.newLine();
                    writer.flush();
                }

            }
        }
    }
}
