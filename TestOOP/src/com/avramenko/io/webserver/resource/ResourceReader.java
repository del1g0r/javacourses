package com.avramenko.io.webserver.resource;

import com.avramenko.io.webserver.entity.Content;
import com.avramenko.io.webserver.entity.StatusCode;
import com.avramenko.io.webserver.expcetion.ServerException;
import com.avramenko.io.webserver.service.MimeConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceReader {

    static private final String DEFAULT_INDEX = "index.html";
    private static final Pattern RESOURCE_PATTERN = Pattern.compile("([^\\?]+)\\??(.*)");

    private String webAppPath;

    public ResourceReader(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public String getContentName(String uri) {
        Matcher matcher;
        if (uri != null && (matcher = RESOURCE_PATTERN.matcher(uri)).matches()) {
            String path = matcher.group(1);
            if (!path.isEmpty() && !path.equals("/"))
                return path;
        }
        return DEFAULT_INDEX;
    }

    public Content getContent(String uri) {
        File file = new File(webAppPath, getContentName(uri));
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return new Content(fileInputStream, MimeConfiguration.getMimeByFile(file));
        } catch (IOException e) {
            throw new ServerException("File not found", e, StatusCode.NOT_FOUND);
        }

    }
}
