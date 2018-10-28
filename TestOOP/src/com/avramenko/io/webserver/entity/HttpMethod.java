package com.avramenko.io.webserver.entity;

public enum HttpMethod {
    GET("GET"), POST("POST");

    private final String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod getHttpMethodByName(String name) {
        HttpMethod[] values = values();
        for (HttpMethod httpMethod : values) {
            if (httpMethod.name.equals(name)) {
                return httpMethod;
            }
        }
        throw new IllegalArgumentException("No http method with name: " + name);
    }
}
