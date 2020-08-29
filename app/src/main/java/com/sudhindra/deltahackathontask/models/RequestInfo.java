package com.sudhindra.deltahackathontask.models;

public class RequestInfo {

    private String url;
    private String body;
    private int code;

    public RequestInfo(String url) {
        this.url = url;
    }

    public RequestInfo(String url, String body, int code) {
        this.url = url;
        this.body = body;
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public int getCode() {
        return code;
    }
}
