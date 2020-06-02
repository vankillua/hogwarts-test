package com.vankillua.enums;

/**
 * @Author KILLUA
 * @Date 2020/6/2 14:07
 * @Description
 */
public enum TagShareEnum {
    /**
     * 仅创建人（及同管理组）
     */
    SHARE_PRIVATE("setSharePrivate", "仅创建人（及同管理组）"),

    /**
     * 指定管理员/应用负责人使用
     */
    SHARE_RANGE("setShareRange", "指定管理员/应用负责人使用"),

    /**
     * 所有管理员
     */
    SHARE_PUBLIC("setSharePublic", "所有管理员");

    private String onClick;
    private String name;

    TagShareEnum(String onClick, String name) {
        this.onClick = onClick;
        this.name = name;
    }

    public String getOnClick() {
        return onClick;
    }

    public String getName() {
        return name;
    }

    public static String getNameByOnClick(String onClick) {
        for (TagShareEnum ts : TagShareEnum.values()) {
            if (ts.getOnClick().equals(onClick)) {
                return ts.getName();
            }
        }
        return null;
    }
}
