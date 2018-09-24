package com.jeanboy.web.demo.constants;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    /**
     * 400 请求错误：API 使用方式不正确/参数缺少/参数错误
     */
    PARAMETER_ERROR(400001, "参数错误"),

    /**
     * 401 请求正确但没有权限：令牌失效/没有权限
     */
    PERMISSION_DENIED(401001, "没有权限"),
    TOKEN_INVALID(401002, "令牌失效"),
    ACCOUNT_NOT_FOUND(401003, "账户不存在"),
    ACCOUNT_ALREADY_EXISTS(401004, "账户已存在"),
    PASSWORD_ERROR(401005, "密码错误"),

    /**
     * 403 有权限但禁止访问：禁止访问/账户被停用
     */
    REQUEST_TOO_FREQUENTLY(403001, "请求太频繁"),
    ACCOUNT_IS_LOCKED(403002, "账户已被停用");


    private final int value;

    private final String reasonPhrase;

    ErrorCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public boolean is400Error() {
        return this.value >= 400000 && this.value < 401000;
    }

    public boolean is401Error() {
        return this.value >= 401000 && this.value < 402000;
    }

    public boolean is403Error() {
        return this.value >= 403000 && this.value < 404000;
    }
}
