package com.avramenko.io.webserver;

import com.avramenko.io.BufferedOutputStream;
import com.avramenko.io.webserver.resource.ResourceReader;
import com.avramenko.io.webserver.service.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int DEFAULT_PORT = 3000;

    private int port;
    private ResourceReader resourceReader;

    public Server(String webAppPath) {
        this(DEFAULT_PORT, webAppPath);
    }

    public Server(int port, String webAppPath) {
        this.port = port;
        resourceReader = new ResourceReader(webAppPath);
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
                     BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

                    RequestHandler requestHandler = new RequestHandler(bufferedReader, outputStream, resourceReader);
                    requestHandler.handle();

                }
            }
        }
    }

}
