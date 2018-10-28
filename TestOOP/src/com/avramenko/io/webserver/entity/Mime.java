package com.avramenko.io.webserver.entity;

public enum Mime {

    IMAGE_GIF("image", "gif"),
    IMAGE_PNG("image", "x-png"),
    IMAGE_JPEG("image", "jpeg"),
    TEXT_CSS("text", "css"),
    TEXT_JS("text", "javascript"),
    TEXT_PLAIN("text", "plain"),
    TEXT_HTML("text", "html");

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public static final Mime DEFAULT = TEXT_HTML;

    private String type;
    private String subType;

    Mime(String type, String subType) {
        this.type = type;
        this.subType = subType;
    }

    public String getValue() {
        return type + '/' + subType;
    }

    @Override
    public String toString() {
        return "Mime{" +
                "type='" + type + '\'' +
                ", subType='" + subType + '\'' +
                '}';
    }
}
