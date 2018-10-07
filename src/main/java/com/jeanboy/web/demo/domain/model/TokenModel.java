package com.jeanboy.web.demo.domain.model;

public class TokenModel {

    private String token;
    private long expires_in;

    public TokenModel() {
    }

    public TokenModel(String token, long expires_in) {
        this.token = token;
        this.expires_in = expires_in;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }
}
