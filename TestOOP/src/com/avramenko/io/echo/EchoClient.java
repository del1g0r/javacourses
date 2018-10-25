package com.avramenko.io.echo;

import java.io.*;
import java.net.Socket;

public class EchoClient {

    static private final String DEFAULT_HOST = "localhost";
    static private final int DEFAULT_PORT = 3000;
    static private final String STOP_WORD = "bye";

    static class Handler implements Runnable {

        private Socket socket;
        private boolean isRunning = true;

        Handler(Socket socket) throws IOException {
            setSocket(socket);
        }

        public Socket getSocket() {
            return socket;
        }

        void setSocket(Socket socket) throws IOException {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
                while (isRunning) {
                    String message = reader.readLine();
                    System.out.println("response from server: " + message);
                    isRunning = !message.toLowerCase().contains(STOP_WORD);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        boolean isRunning = true;
        try (Socket socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ) {
            Thread thread = new Thread(new Handler(socket));
            thread.start();
            while (isRunning) {
                String message = reader.readLine();
                writer.write(message + '\n');
                writer.flush();
                isRunning = !message.toLowerCase().equals(STOP_WORD);
            }
        }
    }
}