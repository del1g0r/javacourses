package com.avramenko.io.webserver;

import java.io.*;

public class ResponseWriter {

    private BufferedWriter socketWriter;

    public ResponseWriter(BufferedWriter socketWriter) {
        this.socketWriter = socketWriter;
    }

    public BufferedWriter getSocketWriter() {
        return socketWriter;
    }

    public void sendResponse(Response response, boolean doFlush) throws IOException {
        socketWriter.write(response.toString());
        socketWriter.write(" \n\n");
        if (doFlush) {
            socketWriter.flush();
        }
    }

    public void sendResponse(Response response) throws IOException {
        sendResponse(response, true);
    }

    public void trySendResponse(Response response) {
        try {
            sendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSuccessResponse(File file) throws IOException {
        //socketWriter.reset();
        try (BufferedReader resourceReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            sendResponse(Response.SUCCESS, false);
            String line;
            while ((line = resourceReader.readLine()) != null) {
                socketWriter.write(line);
                socketWriter.write("\n");
            }
            socketWriter.write("\n");
            socketWriter.flush();
        }
    }
}
