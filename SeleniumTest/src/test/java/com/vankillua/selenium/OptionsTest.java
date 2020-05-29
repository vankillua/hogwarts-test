package com.vankillua.selenium;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author KILLUA
 * @Date 2020/5/27 20:49
 * @Description
 */
public class OptionsTest {
    private static WebDriver driver;
    private static final String TITLE = "企业微信";

    @BeforeAll
    static void before() {
        ChromeOptions options = new ChromeOptions();
        /*
        1. 关闭全部Chrome浏览器
        2. 通过命令行启动chrome：chrome --remote-debugging-port=9001
        3. 打开待测试网页
        4. 登陆
        5. 不关闭浏览器
         */
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9001");
        // new ChromeDriver时设置ChromeOptions
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    void saveCookies() throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            assertThat("当前页面的标题不是<" + TITLE + ">", driver.getTitle(), is(TITLE));
            ObjectMapper objectMapper = new ObjectMapper();
            List<Cookie> list = new ArrayList<>(driver.manage().getCookies());
            String cookiesJsonFile = "src/test/resources/cookies.json";
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cookiesJsonFile)));
            bufferedWriter.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (null != bufferedWriter) {
                bufferedWriter.close();
            }
        }
    }

    @Test
    void searchBaidu() {
        // 无需访问待测试网页，也不会打开一个新的浏览器，而是在当前浏览器直接执行
        driver.findElement(By.id("kw")).sendKeys("bilibili");
        driver.findElement(By.id("su")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void after() {
        if (null != driver) {
            driver.quit();
        }
    }
}
