package com.jeanboy.web.demo.domain.model;

import org.springframework.http.HttpStatus;

public class ResponseModel {

    private int code;
    private String message;
    private String data;

    public ResponseModel(String data) {
        this.code = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
        this.data = data;
    }

    public ResponseModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
