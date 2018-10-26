package com.avramenko.io.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {

    private static final Pattern URI_PATTERN = Pattern.compile("([^\\s]+)\\s+([^\\s]+)\\s*([^\\s]*)");

    public Request parseRequest(BufferedReader reader) throws IOException {
        String httpMethod = null;
        String uri = null;
        String httpVersion;
        Map<String, String> headers = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            if (uri == null) {
                Matcher matcher = URI_PATTERN.matcher(line);
                if (matcher.matches()) {
                    httpMethod = matcher.group(1);
                    uri = matcher.group(2);
                    httpVersion = matcher.group(3);
                }
            } else {
                String[] parts = line.split(":");
                headers.put(parts[0].trim(), parts[1].trim());
            }
        }
        return new Request(uri, httpMethod, headers);
    }
}
