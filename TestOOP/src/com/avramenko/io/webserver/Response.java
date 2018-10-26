package com.avramenko.io.webserver;

public class Response {

    public static final Response SUCCESS = new Response("200", "OK");
    public static final Response NOT_FOUND = new Response("404", "Not Found");
    public static final Response BAD_REQUEST = new Response("400", "Bad Request");
    public static final Response INTERNAL_SERVER_ERROR = new Response("500", "Internal Server Error");

    private String httpProtocol;
    private String statusCode;
    private String reasonLine;

    public Response(String httpProtocol, String statusCode, String reasonLine) {
        this.httpProtocol = httpProtocol;
        this.statusCode = statusCode;
        this.reasonLine = reasonLine;
    }

    public Response(String statusCode, String reasonLine) {
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
