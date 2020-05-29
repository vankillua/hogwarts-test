package com.vankillua.selenium;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

/**
 * @Author KILLUA
 * @Date 2020/5/27 21:59
 * @Description
 */
public class CookieTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void beforeAll() {
        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10, 1000);
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        loadCookies();
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
    }

    private static void loadCookies() {
        try (InputStream is = ClassLoader.getSystemResourceAsStream("cookies.json")) {
            if (Optional.ofNullable(is).isPresent()) {
                List<Map<String, Object>> list = mapper.readValue(is, new TypeReference<List<Map<String, Object>>>() { });
                list.forEach(m -> driver.manage().addCookie(toCookie(m)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Cookie toCookie(Map<String, Object> m) {
        Date expiry = null;
        if (null != m.getOrDefault("expiry", null)) {
            expiry = new Date(Long.parseLong(String.valueOf(m.get("expiry"))));
        }
        Cookie cookie = new Cookie(
                String.valueOf(m.getOrDefault("name", "")),
                String.valueOf(m.getOrDefault("value", null)),
                String.valueOf(m.getOrDefault("domain", null)),
                String.valueOf(m.getOrDefault("path", null)),
                expiry,
                Boolean.parseBoolean(String.valueOf(m.getOrDefault("secure", false))),
                Boolean.parseBoolean(String.valueOf(m.getOrDefault("httpOnly", false))));
        return cookie;
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    @Test
    void menuTest() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu_contacts"))).click();
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu_apps"))).click();
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu_customer"))).click();
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu_manageTools"))).click();
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu_profile"))).click();
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("menu_index"))).click();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void afterAll() {
        if (null != driver) {
            driver.quit();
        }
    }
}
