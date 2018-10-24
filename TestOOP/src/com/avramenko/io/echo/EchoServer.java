package com.avramenko.io.echo;

import com.avramenko.datastructures.list.ArrayList;
import com.avramenko.datastructures.list.List;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

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
            System.out.println("New connection: " + socket.getRemoteSocketAddress());
            try {
                while (isRunning) {
                    InputStream inputStream = socket.getInputStream();
                    int count = inputStream.read(buffer);
                    String message;
                    isRunning = !(message = new String(buffer, 0, count)).toLowerCase().equals(STOP_WORD);
                    System.out.println("Got a message: " + message);
                    message = "Echo:" + message;
                    socket.getOutputStream().write(message.getBytes());
                }
                System.out.println("Free connection: " + socket.getRemoteSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static private List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    Thread thread = new Thread(new Handler(socket));
                    threads.add(thread);
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}