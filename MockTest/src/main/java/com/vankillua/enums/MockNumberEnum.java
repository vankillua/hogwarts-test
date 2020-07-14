package com.vankillua.enums;

/**
 * @Author KILLUA
 * @Date 2020/7/14 12:49
 * @Description
 */
public enum MockNumberEnum {
    /**
     * -1: 开始
     */
    BEGIN(-1, "开始"),

    /**
     * 0: 负整数
     */
    NEGATIVE_INTEGER(0, "负整数"),

    /**
     * 1: 负浮点数
     */
    NEGATIVE_FLOAT(1, "负浮点数"),

    /**
     * 2: 零
     */
    ZERO(2, "零"),

    /**
     * 3: 正浮点数
     */
    POSITIVE_FLOAT(3, "正浮点数"),

    /**
     * 4: 正整数
     */
    POSITIVE_INTEGER(4, "正整数"),

    /**
     * 5: 结束
     */
    END(5, "结束");

    private final int code;
    private final String name;

    MockNumberEnum(int code, String name) {
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
        for (MockNumberEnum mn : MockNumberEnum.values()) {
            if (mn.getCode() == code) {
                return mn.getName();
            }
        }
        return null;
    }
}
