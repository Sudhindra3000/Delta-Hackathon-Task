package com.sudhindra.deltahackathontask.models;

import com.sudhindra.deltahackathontask.constants.RequestType;

public class RequestInfo {

    private RequestType requestType;
    private String url;
    private String requestBody;

    private String responseBody;
    private int code;

    public RequestInfo(RequestType requestType, String url, String requestBody, String responseBody, int code) {
        this.requestType = requestType;
        this.url = url;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.code = code;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getUrl() {
        return url;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public int getCode() {
        return code;
    }
}
