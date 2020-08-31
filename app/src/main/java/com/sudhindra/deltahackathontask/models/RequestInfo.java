package com.sudhindra.deltahackathontask.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public static String prettifyJson(String s) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(s);
        return gson.toJson(je);
    }
}
