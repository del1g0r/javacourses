package com.avramenko.io.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static public final int DEFAULT_PORT = 3000;

    private int port;
    private ResourceReader resourceReader;
    private RequestParser requestParser;

    public Server(int port, String webAppPath) {
        this.port = port;
        resourceReader = new ResourceReader(webAppPath);
        requestParser = new RequestParser();
    }

    public String getWebAppPath() {
        return resourceReader.getWebAppPath();
    }

    public int getPort() {
        return port;
    }


    void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ) {
                    RequestHandler requestHandler = new RequestHandler(bufferedReader, bufferedWriter, resourceReader, requestParser);
                    requestHandler.start();
                }
            }
        }
    }

}
