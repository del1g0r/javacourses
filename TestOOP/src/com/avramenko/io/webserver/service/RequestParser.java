package com.avramenko.io.webserver.service;

import com.avramenko.io.webserver.entity.HttpMethod;
import com.avramenko.io.webserver.entity.Request;
import com.avramenko.io.webserver.entity.StatusCode;
import com.avramenko.io.webserver.exception.StatusCodeNotFoundException;
import com.avramenko.io.webserver.expcetion.ServerException;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public static Request parseRequest(BufferedReader reader) {
        try {
            String httpMethod = null;
            String uri = null;
            Map<String, String> headers = new HashMap<>();
            String line;

            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                if (uri == null) {
                    String[] split = line.split("\\s");
                    if (split.length >= 2) {
                        httpMethod = split[0];
                        uri = split[1];
                        if (!httpMethod.equals("GET")) {
                            throw new ServerException("Method not supported", null, StatusCode.METHOD_NOT_ALLOWED);
                        }
                    } else {
                        throw new StatusCodeNotFoundException("Invalid status code", null);
                    }
                } else {
                    String[] parts = line.split(":");
                    headers.put(parts[0].trim(), parts[1].trim());
                }
            }
            return new Request(uri, HttpMethod.GET, headers);
        } catch (Exception e) {
            throw new ServerException("Cannot parse request", e, StatusCode.BAD_REQUEST);
        }
    }
}

