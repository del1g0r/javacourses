package com.avramenko.io.webserver;

import java.util.Map;

public class Request {

    private String uri;
    private String httpMethod;
    private Map<String, String> headers;

    public Request(String uri, String httpMethod, Map<String, String> headers) {
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.headers = headers;
    }

    public String getUri() {
        return uri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Request) {
            Request that = (Request) obj;
            return uri.equals(that.uri)
                    && httpMethod.equals(that.httpMethod)
                    && headers.equals(that.headers);
        }
        return false;
     }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Request{)");
        stringBuilder.append("uri='");
        stringBuilder.append(uri);
        stringBuilder.append('\'');
        stringBuilder.append(", httpMethod=");
        stringBuilder.append(httpMethod);
        stringBuilder.append('\'');
        stringBuilder.append(", headers=");
        stringBuilder.append(headers);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
