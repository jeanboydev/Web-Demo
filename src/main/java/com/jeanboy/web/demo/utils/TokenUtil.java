package com.jeanboy.web.demo.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.UUID;

public class TokenUtil {

    public static final long EXPIRES_IN = 2 * 60 * 60;//2小时

    /**
     * 返回 10 位数字，不够前面补 0
     *
     * @param number
     * @return
     */
    private static String numberFormat(long number) {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(10);
        format.setMinimumIntegerDigits(10);
        return format.format(number);
    }

    /**
     * 获取 token
     *
     * @param userId
     * @return
     */
    public static String getToken(long userId) {
        try {
            String token = numberFormat(userId) + "_" +
                    (System.currentTimeMillis() + EXPIRES_IN * 1000) +
                    UUID.randomUUID();
            System.out.println("==========getToken============" + token);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] sha256 = md.digest(token.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(sha256);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
