package com.vankillua;

import org.openqa.selenium.WebDriverException;

/**
 * @Author KILLUA
 * @Date 2020/5/31 21:51
 * @Description
 */
public class AlertException extends RuntimeException {
    public AlertException(String message) {
        super("错误提示：" + message);
    }
}
