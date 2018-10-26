package com.avramenko.io.webserver;

import java.io.*;

public class WebServer {

    public static void main(String[] args) throws IOException {
        Server server = new Server(Server.DEFAULT_PORT, "resource/webapp");
        server.start();
    }
}