package com.avramenko.io.webserver;

import java.io.*;

public class RequestHandler {

    private BufferedReader bufferedReader;
    private ResponseWriter responseWriter;
    private ResourceReader resourceReader;
    private RequestParser requestParser;


    public RequestHandler(BufferedReader bufferedReader, BufferedWriter bufferedWriter, ResourceReader resourceReader, RequestParser requestParser) {
        this.bufferedReader = bufferedReader;
        this.requestParser = requestParser;
        this.resourceReader = resourceReader;
        responseWriter = new ResponseWriter(bufferedWriter);
    }

    public void start() {
        try {
            Request request = null;
            try {
                request = requestParser.parseRequest(bufferedReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (request == null) {
                responseWriter.sendResponse(Response.BAD_REQUEST);
            } else {
                File file = resourceReader.getContentFile(request.getUri());
                System.out.println(request.getUri());
                if (!file.exists()) {
                    responseWriter.sendResponse(Response.NOT_FOUND);
                } else {
                    responseWriter.sendSuccessResponse(file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseWriter.trySendResponse(Response.INTERNAL_SERVER_ERROR);//        threads.remove(this);
        }
    }
}
