package com.avramenko.io.echo;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    static private final String DEFAULT_HOST = "localhost";
    static private final int DEFAULT_PORT = 3000;
    static private final int DEFAULT_BUFFER_SIZE = 1024;
    static private final String STOP_WORD = "bye";

    static class Handler implements Runnable {

        private byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        private Socket socket;
        private boolean isRunning = true;

        Handler(Socket socket) {
            setSocket(socket);
        }

        public Socket getSocket() {
            return socket;
        }

        void setSocket(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    int count = socket.getInputStream().read(buffer);
                    String message = new String(buffer, 0, count);
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
        Socket socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
        Thread thread = new Thread(new Handler(socket));
        thread.start();
        Scanner scanner = new Scanner(System.in);
        while (isRunning) {
            String message = scanner.nextLine();
            socket.getOutputStream().write(message.getBytes());
            isRunning = !message.toLowerCase().equals(STOP_WORD);
        }
    }
}