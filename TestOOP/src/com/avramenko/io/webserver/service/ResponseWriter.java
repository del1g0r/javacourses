package com.avramenko.io.webserver.service;

import com.avramenko.io.webserver.entity.Content;
import com.avramenko.io.webserver.entity.StatusCode;
import com.avramenko.io.webserver.expcetion.ServerException;

import java.io.*;
import java.util.HashMap;

public class ResponseWriter {

    private OutputStream outputStream;
    private OutputStreamWriter writer;

    public ResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.writer = new OutputStreamWriter(outputStream);
    }

    public void sendResponse(StatusCode statusCode, HashMap<String, String> headers, boolean doFlush) throws IOException {
        writer.write(statusCode.toString());
        writer.write(" \n");
        if (headers != null) {
            headers.forEach((k, v) -> {
                try {
                    writer.write(k);
                    writer.write(": ");
                    writer.write(v);
                    writer.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        writer.write("\n");
        if (doFlush) {
            writer.flush();
        }
    }

    public void sendResponse(StatusCode statusCode, boolean doFlush) throws IOException {
        sendResponse(statusCode, null, doFlush);
    }

    private void sendResponse(StatusCode statusCode) throws IOException {
        sendResponse(statusCode, true);
    }

    public void trySendResponse(StatusCode statusCode) {
        try {
            sendResponse(statusCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSuccessResponse(Content content) {
        try (BufferedInputStream inputStream = new BufferedInputStream(content.getInputStream())) {
            sendResponse(StatusCode.SUCCESS, new HashMap<String, String>() {
                String str = put("content-type", content.getMime().getValue());
            }, false);
            if (content.getMime().getType().equals("text")) {
                try (BufferedReader resourceReader = new BufferedReader(new InputStreamReader(content.getInputStream()));) {
                    String line;
                    while ((line = resourceReader.readLine()) != null) {
                        writer.write(line);
                        writer.write("\n");
                    }
                    writer.write("\n");
                    writer.flush();
                }
            } else {
                byte[] buf = new byte[1024];
                int count;
                while ((count = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, count);
                }
                outputStream.flush();
            }

        } catch (IOException e) {
            throw new ServerException("error!", e, StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
