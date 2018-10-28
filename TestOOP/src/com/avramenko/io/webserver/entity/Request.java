package com.avramenko.io.webserver.entity;

import java.util.Map;

public class Request {

    private String uri;
    private HttpMethod httpMethod;
    private Map<String, String> headers;

    public Request() {
    }

    public Request(String uri, HttpMethod httpMethod, Map<String, String> headers) {
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.headers = headers;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
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
