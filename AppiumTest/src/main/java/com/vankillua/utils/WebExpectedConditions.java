package com.vankillua.utils;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @Author KILLUA
 * @Date 2020/7/3 17:09
 * @Description
 */
public class WebExpectedConditions {
    private static final Logger logger = LoggerFactory.getLogger(WebExpectedConditions.class);

    private WebExpectedConditions() {}

    public static ExpectedCondition<WebDriver> windowToBeAvailableAndSwitchToIt(String nameOrHandleOrTitle) {
        return new ExpectedCondition<WebDriver>() {
            @Override
            public WebDriver apply(WebDriver driver) {
                try {
                    return driver.switchTo().window(nameOrHandleOrTitle);
                } catch (NoSuchWindowException windowWithNameOrHandleNotFound) {
                    try {
                        return windowByTitle(driver, nameOrHandleOrTitle);
                    } catch (NoSuchWindowException e) {
                        return null;
                    }
                }
            }

            @Override
            public String toString() {
                return "window to be available by name or handle or title: " + nameOrHandleOrTitle;
            }
        };
    }

    protected static WebDriver windowByTitle(WebDriver driver, String title) {
        Set<String> windowHandles = driver.getWindowHandles();

        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);
            if (title.equals(driver.getTitle())) {
                return driver;
            }
        }
        throw new NoSuchWindowException("Window with title not found: " + title);
    }
}
