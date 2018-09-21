package com.jeanboy.web.demo.constants;

import java.util.HashMap;
import java.util.Map;

public class HttpStatus {

    public static int STATUS_200 = 200;// OK
    public static int STATUS_201 = 201;// Created
    public static int STATUS_202 = 202;// Accepted
    public static int STATUS_204 = 204;// No Content
    public static int STATUS_400 = 400;// Bad Request
    public static int STATUS_401 = 401;// Unauthorized
    public static int STATUS_403 = 403;// Forbidden
    public static int STATUS_404 = 404;// Not Found
    public static int STATUS_405 = 405;// Method Not Allowed
    public static int STATUS_406 = 406;// Not Acceptable
    public static int STATUS_410 = 410;// Gone
    public static int STATUS_422 = 422;// Unprocessable Entity
    public static int STATUS_429 = 429;// Too Many Requests
    public static int STATUS_500 = 500;// Internal Server Error

    private static Map<Integer, String> messageMap = new HashMap<>();

    static {
        messageMap.put(STATUS_200, "OK");
        messageMap.put(STATUS_201, "Created");
        messageMap.put(STATUS_202, "Accepted");
        messageMap.put(STATUS_204, "No Content");
        messageMap.put(STATUS_400, "Bad Request");
        messageMap.put(STATUS_401, "Unauthorized");
        messageMap.put(STATUS_403, "Forbidden");
        messageMap.put(STATUS_404, "Not Found");
        messageMap.put(STATUS_405, "Method Not Allowed");
        messageMap.put(STATUS_406, "Not Acceptable");
        messageMap.put(STATUS_410, "Gone");
        messageMap.put(STATUS_422, "Unprocessable Entity");
        messageMap.put(STATUS_429, "Too Many Requests");
        messageMap.put(STATUS_500, "Internal Server Error");
    }

    public static String getMessage(int code){
        return messageMap.get(code);
    }
}
