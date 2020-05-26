package com.vankillua.selenium;

import com.vankillua.selenium.utils.WebElementUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTest {
    private static final String url = "https://home.testing-studio.com/";
    private static final String username = "270793499@qq.com";
    private static final String password = "killua2BOE";

    private static WebDriver driver;
    private static Wait<WebDriver> wait;

    @BeforeAll
    static void initWebDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
    }

    @Test
    @Order(1)
    void login() {
        System.setProperty("login.status", "logout");
        driver.get(url);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".login-button"))).click();
        WebElementUtils.clearAndSendKeys(wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#login-account-name"))), username);
        WebElementUtils.clearAndSendKeys(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='login-account-password']"))), password);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#login-button"))).click();
        Assertions.assertNotNull(wait.until(ExpectedConditions.presenceOfElementLocated(By.id("current-user"))), "登陆失败！");
        System.setProperty("login.status", "login");
        System.out.println("登陆成功！");
    }

    @Test
    @Order(2)
    void stay() throws InterruptedException {
        System.out.println("login.status: " + System.getProperty("login.status", "null"));
        Thread.sleep(5000);
    }

    @Test
    @Order(3)
    @EnabledIfSystemProperty(named = "login.status", matches = "login")
    void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("current-user"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".user-menu .user>a.user-activity-link"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".user-menu .logout"))).click();
        Assertions.assertNotNull(wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".login-button"))), "登出失败！");
        System.setProperty("login.status", "logout");
        System.out.println("登出成功！");
    }

    @AfterAll
    static void closeWebDriver() {
        System.clearProperty("login.status");
        if (null != driver) {
            driver.quit();
        }
    }
}
