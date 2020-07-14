package com.vankillua.enums;

/**
 * @Author KILLUA
 * @Date 2020/7/14 12:59
 * @Description
 */
public enum MockStringEnum {
    /**
     * -1: 开始
     */
    BEGIN(-1, "开始"),

    /**
     * 0: 空字符串
     */
    EMPTY_STRING(0, "空字符串"),

    /**
     * 1: 中文字符串
     */
    CHINESE_STRING(1, "中文字符串"),

    /**
     * 2: 英文字符串
     */
    ENGLISH_STRING(2, "英文字符串"),

    /**
     * 3: 特殊字符字符串
     */
    SPECIAL_STRING(3, "特殊字符字符串"),

    /**
     * 4: null字符串
     */
    NULL_STRING(4, "null字符串"),

    /**
     * 5: 结束
     */
    END(5, "结束");

    private final int code;
    private final String name;

    MockStringEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(int code) {
        for (MockStringEnum ms : MockStringEnum.values()) {
            if (ms.getCode() == code) {
                return ms.getName();
            }
        }
        return null;
    }
}
