package com.fanglin.test.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 加密工具类
 *
 * @author 彭方林
 * @version 1.0
 * @date 2019/6/11 15:49
 **/
public class EncodeUtils {
    /**
     * 盐，用于混交md5
     */
    private static final String SLAT = "123456789";

    public static String md5(String content) {
        try {
            content += SLAT;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(content.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = m.digest();
            StringBuilder result = new StringBuilder();
            for (byte aByte : bytes) {
                result.append(Integer.toHexString((0x000000FF & aByte) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("加密失败");
        }

    }
}
