package com.example.chat.basic;

import java.io.*;
import java.net.Socket;

public class CustomClient {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 3000;

        try (Socket socket = new Socket(host, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String msg = "Hello World";

            out.write(msg);
            out.flush();
        }
    }
}
