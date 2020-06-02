package com.vankillua.page;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author KILLUA
 * @Date 2020/5/31 15:21
 * @Description
 */
class MainPageTest {
    private static MainPage mainPage;
    private static final String CONTACT_PAGE_ID = "menu_contacts";

    @BeforeAll
    static void beforeAll() throws IOException {
        mainPage = new MainPage();
    }

    @Test
    void toContactPage() throws InterruptedException {
        ContactPage contactPage = mainPage.toContactPage();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw e;
        }
        WebElement element = contactPage.getCurrentFrame();
        assertThat("当前页面不是[通讯录]页面", element.getAttribute("id"), is(CONTACT_PAGE_ID));
    }

    void logoutWeChatWork() {
        mainPage.logout();
    }

    @AfterAll
    static void afterAll() {
        if (null != mainPage) {
            mainPage.quit();
        }
    }
}