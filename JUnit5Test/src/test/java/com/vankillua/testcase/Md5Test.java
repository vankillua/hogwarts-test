package com.vankillua.testcase;

import com.vankillua.utils.Md5Util;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @Author KILLUA
 * @Date 2020/5/27 9:09
 * @Description
 */
public class Md5Test {
    @Test
    void sumMd5() {
        String str = "cy&sindy love 1314";
        int total0 = 0;
        int total1 = 0;
        System.out.println("str: " + str);
        String md5Str = Md5Util.encodeWithoutSalt(str).toLowerCase();
        System.out.println("md5Str: " + md5Str);

        for (byte b : md5Str.getBytes(StandardCharsets.UTF_8)) {
            int zero = 0;
            int one = 0;
            String bStr = String.format("%8s", Integer.toBinaryString(b)).replaceAll(" ", "0");
            for (char c : bStr.toCharArray()) {
                if ('0' == c) {
                    zero++;
                } else if ('1' == c) {
                    one++;
                }
            }
            total0 += zero;
            total1 += one;
//            System.out.println("bStr: " + bStr + ", zero: " + zero + ", one: " + one);
        }
        System.out.printf("zero: %03d, one: %03d\n", total0, total1);
    }
}
