package com.avramenko.io.echo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class EchoServer {

    static private final int DEFAULT_PORT = 3000;
    static private final String STOP_WORD = "bye";

    static class HandlerThread extends Thread {

        private Socket socket;
        private boolean isRunning = true;

        HandlerThread(Socket socket) {
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
            threads.add(this);
            System.out.println("New connection: " + socket.getRemoteSocketAddress());
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {
                while (isRunning) {
                    String message;
                    isRunning = !(message = reader.readLine()).toLowerCase().equals(STOP_WORD);
                    System.out.println("Got a message: " + message);
                    writer.write("Echo:" + message + '\n');
                    writer.flush();
                }
                System.out.println("Free connection: " + socket.getRemoteSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
            threads.remove(this);
        }

        static private List<Thread> threads = new ArrayList<>();

        public static void main(String[] args) throws IOException {
            try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        Thread thread = new HandlerThread(socket);
                        threads.add(thread);
                        thread.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}