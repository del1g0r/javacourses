package com.avramenko.io.webserver.service;

import com.avramenko.io.webserver.entity.Content;
import com.avramenko.io.webserver.entity.Request;
import com.avramenko.io.webserver.expcetion.ServerException;
import com.avramenko.io.webserver.resource.ResourceReader;

import java.io.BufferedReader;
import java.io.OutputStream;

public class RequestHandler {

    private BufferedReader bufferedReader;
    private ResponseWriter responseWriter;
    private ResourceReader resourceReader;

    public RequestHandler(BufferedReader bufferedReader, OutputStream outputStream, ResourceReader resourceReader) {
        this.bufferedReader = bufferedReader;
        this.resourceReader = resourceReader;
        responseWriter = new ResponseWriter(outputStream);
    }

    public void handle() {
        try {
            Request request = RequestParser.parseRequest(bufferedReader);
            Content content = resourceReader.getContent(request.getUri());
            responseWriter.sendSuccessResponse(content);
        } catch (ServerException e) {
            responseWriter.trySendResponse(e.getStatusCode());//        threads.remove(this);
        }
    }
}
