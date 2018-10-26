package com.avramenko.io.webserver;

import java.io.File;
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
        try {
            Matcher matcher = RESOURCE_PATTERN.matcher(uri);
            if (matcher.matches()) {
                String path = matcher.group(1);
                if (!path.isEmpty() && !path.equals("/"))
                    return path;
            }
        } catch (NullPointerException e) {
        }
        return DEFAULT_INDEX;
    }

    public File getContentFile(String uri) {
        return new File(webAppPath, getContentName(uri));
    }
}
