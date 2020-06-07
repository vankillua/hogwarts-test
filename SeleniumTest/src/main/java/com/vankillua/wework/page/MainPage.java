package com.vankillua.wework.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

/**
 * @Author KILLUA
 * @Date 2020/5/31 14:48
 * @Description
 */
public class MainPage extends BasePage {
    private static final String WE_CHAT_WORK_URL = "https://work.weixin.qq.com/wework_admin/frame";

    /**
     * 上方菜单栏（首页，通讯录，应用管理，客户联系，管理工具，我的企业）
     */
    private static final By INDEX_MENU = By.id("menu_index");
    private static final By CONTACTS_MENU = By.id("menu_contacts");

    /**
     * 菜单页面
     */
    private static final By INDEX_FRAME = By.cssSelector("#main>[data-name=\"index\"]");
    private static final By CONTACTS_FRAME = By.cssSelector("#main>[data-name=\"contacts\"]");

    /**
     * 上方按钮（API文档，联系客服，退出）
     */
    private static final By LOGOUT_BUTTON = By.id("logout");

    public MainPage() throws IOException {
        get(WE_CHAT_WORK_URL);
        loadCookies();
        get(WE_CHAT_WORK_URL);
    }

    public MainPage toMainPage() {
        click(INDEX_MENU);
        wait.until(ExpectedConditions.visibilityOfElementLocated(INDEX_FRAME));
        return this;
    }

    public ContactPage toContactPage() {
        click(CONTACTS_MENU);
        wait.until(ExpectedConditions.visibilityOfElementLocated(CONTACTS_FRAME));
        return new ContactPage(driver, wait);
    }

    public LogoutPage logout() {
        click(LOGOUT_BUTTON);
        return new LogoutPage();
    }
}
