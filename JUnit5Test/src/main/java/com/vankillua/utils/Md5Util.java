package com.vankillua.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author KILLUA
 * @Date 2020/5/27 9:14
 * @Description
 */
public class Md5Util {

    private static String byteToHexString(byte[] b) {
        StringBuilder hexString = new StringBuilder();

        for (byte value : b) {
            String hex = Integer.toHexString(value & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    public static String encodeWithoutSalt(String str) {
        String encodeStr = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            encodeStr = byteToHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
}
