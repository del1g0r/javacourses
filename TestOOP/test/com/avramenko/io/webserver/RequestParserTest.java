package com.avramenko.io.webserver;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class RequestParserTest {

    RequestParser requestParser;

    BufferedReader getBufferedReader(String value) {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(value.getBytes())));
    }

    @Before
    public void before() {
        requestParser = new RequestParser();
    }

    @Test
    public void testParseRequest() throws IOException {
        Request expected = new Request("/", "GET", new HashMap<String, String>() {
            String value = put("Host", "www.sample.com");
        });
        Request actual = requestParser.parseRequest(getBufferedReader("GET / HTTP/1.1\nHost: www.sample.com\n\n"));
        assertEquals(expected, actual);

        expected = new Request("/index.html?q=1", "GET", new HashMap<>());
        actual = requestParser.parseRequest(getBufferedReader("GET /index.html?q=1 HTTP/1.1\n\n"));
        assertEquals(expected, actual);
    }
}
