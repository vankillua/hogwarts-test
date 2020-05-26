package com.vankillua.selenium.utils;

import org.openqa.selenium.WebElement;

/**
 * @author KILLUA
 */
public class WebElementUtils {

    protected WebElementUtils() {
    }

    public static void clearAndSendKeys(WebElement element, CharSequence... keysToSend) {
        element.clear();
        element.sendKeys(keysToSend);
    }
}
