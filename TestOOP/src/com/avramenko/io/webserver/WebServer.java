package com.avramenko.io.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServer {

    static private final int DEFAULT_PORT = 3000;
    static private final String RESOURCE_PATTERN = "GET (\\/[^? ]*)(\\?*.*)";
    static private final String DEFAULT_INDEX = "index.html";

    private int port;
    private String appPath;
    private List<Thread> threads = new ArrayList<>();

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    Thread thread = new HandlerThread(socket);
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class HandlerThread extends Thread {

        private Socket socket;

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
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {
                String line;
                String requestLine = null;
                Map<String, String> requestParameters = new HashMap<>();
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    if (requestLine == null) {
                        requestLine = line;
                    } else {
                        String[] parts = line.split(":");
                        requestParameters.put(parts[0], parts[1].trim());
                    }
                }
                String resourceName = requestLine.replaceAll(WebServer.RESOURCE_PATTERN, "$1");
                if (resourceName.isEmpty() || resourceName.equals("/")) {
                    resourceName = WebServer.DEFAULT_INDEX;
                }
                System.out.println("resource is requested:" + resourceName);
                try (BufferedReader resourceReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(appPath, resourceName))));) {
                    writer.write("HTTP/1.1 200 OK\n");
                    writer.write("\n");
                    while ((line = resourceReader.readLine()) != null) {
                        writer.write(line);
                        writer.write("\n");
                    }
                    writer.write("\n");
                    writer.flush();
                } catch (IOException e) {
                    writer.write("HTTP/1.1 402 Payment Required\n"); // :)
                    writer.write("\n");
                    writer.flush();
                    e.printStackTrace();
                }
                System.out.println("Free connection: " + socket.getRemoteSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
            threads.remove(this);
        }
    }

    public static void main(String[] args) throws IOException {
        WebServer server = new WebServer();
        server.setPort(WebServer.DEFAULT_PORT);
        server.setAppPath("resource/webapp");
        server.start();
    }
}