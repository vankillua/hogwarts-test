package com.vankillua.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @Author KILLUA
 * @Date 2020/7/13 10:32
 * @Description
 */
@Slf4j
public class RandomUtils {
    private static final int DEFAULT_LENGTH = 5;
    private static final int DEFAULT_INTEGER_RANGE = 10;
    private static final String SPECIAL_CHARS = "`~!@#$%^&*()+=|{}':;',[].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？";

    private static final int HIGH_BYTE_1 = 0xB0;
    private static final int HIGH_BYTE_2 = 0xF7;
    private static final int LOW_BYTE_1 = 0xA1;
    private static final int LOW_BYTE_2 = 0xFE;

    private static final Random RANDOM = new Random();

    private RandomUtils() {}

    public static Integer randomPositiveInteger(int length) {
        if (0 == length) {
            return 0;
        }
        return Integer.valueOf(RandomStringUtils.randomNumeric(0 > length ? DEFAULT_LENGTH : length));
    }

    public static Integer randomNegativeInteger(int length) {
        if (0 == length) {
            return 0;
        }
        return Math.negateExact(Integer.parseInt(RandomStringUtils.randomNumeric(0 > length ? DEFAULT_LENGTH : length)));
    }

    public static Integer randomInteger(int minVal, int maxVal) {
        if (minVal > maxVal) {
            return RANDOM.nextInt(DEFAULT_INTEGER_RANGE);
        }
        return Double.valueOf(RANDOM.nextDouble() * (maxVal - minVal) + minVal).intValue();
    }

    public static Float randomFloat(int minVal, int maxVal) {
        if (minVal > maxVal) {
            return RANDOM.nextFloat();
        }
        return RANDOM.nextFloat() * (maxVal - minVal) + minVal;
    }

    public static Double randomDouble(int minVal, int maxVal) {
        if (minVal < maxVal) {
            return RANDOM.nextDouble();
        }
        return RANDOM.nextDouble() * (maxVal - minVal) + minVal;
    }

    public static String randomEnglishString(int length) {
        return RandomStringUtils.randomAlphabetic(Math.abs(length));
    }

    public static String randomChineseString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Math.abs(length); i++) {
            stringBuilder.append(getRandomChineseChar());
        }
        return stringBuilder.toString();
    }

    public static String randomSimplifiedChineseString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String str = "";
            // 定义高低位
            int highByte;
            int lowByte;

            // 获取高位值 和 低位值
            highByte = (HIGH_BYTE_1 + Math.abs(RANDOM.nextInt(HIGH_BYTE_2 - HIGH_BYTE_1)));
            lowByte = (LOW_BYTE_1 + Math.abs(RANDOM.nextInt(LOW_BYTE_2 - LOW_BYTE_1)));
            byte[] b = new byte[2];
            b[0] = (byte) highByte;
            b[1] = (byte) lowByte;
            try {
                // 转成GBK编码的字符串
                str = new String(b, "GBK");
            } catch (UnsupportedEncodingException e) {
                log.error("字节转成GBK编码字符串时遇到异常：", e);
            }
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    public static String randomSpecialString(int length) {
        return RandomStringUtils.random(length, SPECIAL_CHARS);
    }

    private static char getRandomChineseChar() {
        return (char) (0x4e00 + (new Random().nextInt() * (0x9fa5 - 0x4e00 + 1)));
    }
}
