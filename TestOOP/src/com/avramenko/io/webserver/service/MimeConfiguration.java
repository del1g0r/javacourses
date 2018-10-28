package com.avramenko.io.webserver.service;

import com.avramenko.io.webserver.entity.Mime;

import java.io.File;

public class MimeConfiguration {

    public static Mime getMimeByFile(File file) {
        String fileName = file.getAbsolutePath();
        int i = fileName.lastIndexOf('.');
        String extension = i > 0 ? fileName.substring(i + 1).trim().toLowerCase() : "";
        switch (extension) {
            case "png":
                return Mime.IMAGE_PNG;
            case "gif":
                return Mime.IMAGE_GIF;
            case "jpg":
            case "jpeg":
                return Mime.IMAGE_JPEG;
            case "css":
                return Mime.TEXT_CSS;
            case "html":
            case "htm":
                return Mime.TEXT_HTML;
            case "txt":
                return Mime.TEXT_PLAIN;
            case "js":
                return Mime.TEXT_JS;
            default:
                return Mime.DEFAULT;
        }
    }
}
