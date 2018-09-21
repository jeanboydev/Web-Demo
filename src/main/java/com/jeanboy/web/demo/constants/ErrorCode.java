package com.jeanboy.web.demo.constants;

import java.util.HashMap;
import java.util.Map;

public class ErrorCode {

    /**
     * 400 请求出错，参数缺失，不支持的请求
     * 401 未授权，没有登录，refreshToken失效，已存在
     * 403 没有权限，禁止访问 accessToken失效，权限不足，参数失效
     */


    public static long CODE_TOKEN_INVALID = 1001;// 令牌失效-401
    public static long CODE_PERMISSION_DENIED = 1002;// 没有权限-403
    public static long CODE_PARAMETER_ERROR = 1003;// 参数错误-400
    public static long CODE_ACCOUNT_NOT_FOUND = 1004;// 账户不存在-401
    public static long CODE_ACCOUNT_ALREADY_EXISTS = 1005;// 账户已存在-401
    public static long CODE_PASSWORD_ERROR = 1006;// 密码错误-401


    private static Map<Long, String> messageMap = new HashMap<>();

    static {
        messageMap.put(CODE_TOKEN_INVALID, "令牌失效");
        messageMap.put(CODE_PERMISSION_DENIED, "没有权限");
        messageMap.put(CODE_PARAMETER_ERROR, "参数错误");
        messageMap.put(CODE_ACCOUNT_NOT_FOUND, "账户不存在");
        messageMap.put(CODE_ACCOUNT_ALREADY_EXISTS, "账户已存在");
        messageMap.put(CODE_PASSWORD_ERROR, "密码错误");
    }

    public static String getMessage(long code){
        return messageMap.get(code);
    }

}
