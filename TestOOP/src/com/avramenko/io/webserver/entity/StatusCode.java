package com.avramenko.io.webserver.entity;

public enum StatusCode {

    SUCCESS("200", "OK"),
    NOT_FOUND("404", "Not Found"),
    BAD_REQUEST("400", "Bad Request"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed");

    private String httpProtocol;
    private String statusCode;
    private String reasonLine;

    StatusCode(String httpProtocol, String statusCode, String reasonLine) {
        this.httpProtocol = httpProtocol;
        this.statusCode = statusCode;
        this.reasonLine = reasonLine;
    }

    StatusCode(String statusCode, String reasonLine) {
        this("HTTP/1.1", statusCode, reasonLine);
    }

    public String getHttpProtocol() {
        return httpProtocol;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getReasonLine() {
        return reasonLine;
    }

    @Override
    public String toString() {
        StringBuilder stringBulder = new StringBuilder();
        stringBulder.append(httpProtocol);
        stringBulder.append(' ');
        stringBulder.append(statusCode);
        stringBulder.append(' ');
        stringBulder.append(reasonLine);
        stringBulder.append(' ');
        System.out.println(stringBulder.toString());
        return stringBulder.toString();
    }
}