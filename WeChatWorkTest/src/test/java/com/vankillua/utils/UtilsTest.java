package com.vankillua.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author KILLUA
 * @Date 2020/6/2 9:17
 * @Description
 */
public class UtilsTest {
    @BeforeAll
    static void before() {
    }

    private WebDriver initDriver() {
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
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    private void quitDriver(WebDriver driver) {
        if (null != driver) {
            driver.quit();
        }
    }

    @Test
    void saveCookies() throws IOException {
        WebDriver driver = null;
        final String TITLE = "企业微信";
        BufferedWriter bufferedWriter = null;
        try {
            driver = initDriver();

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
            quitDriver(driver);
        }
    }

    @AfterAll
    static void after() {
    }
}
