package com.jeanboy.web.demo.exceptions.info;

public class ErrorInfo {

    private long code;
    private String description;

    public ErrorInfo(long code, String description) {
        this.code = code;
        this.description = description;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
