package com.avramenko.io.webserver;

import java.io.IOException;

public class WebServer {

    public static void main(String[] args) throws IOException {
        Server server = new Server("resources/webapp");
        server.start();
    }
}