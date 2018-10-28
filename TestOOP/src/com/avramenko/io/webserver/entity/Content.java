package com.avramenko.io.webserver.entity;

import java.io.InputStream;

public class Content {

    private InputStream inputStream;
    private Mime mime;

    public Content(InputStream inputStream, Mime mime) {
        this.inputStream = inputStream;
        this.mime = mime;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public Mime getMime() {
        return mime;
    }
}
