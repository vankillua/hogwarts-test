package com.vankillua.page;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Author KILLUA
 * @Date 2020/6/6 13:01
 * @Description
 */
public class QuotationEditPage extends BasePage {
    private BasePage prePage;

    /**
     * 管理页面右上角的完成按钮
     * 管理页面右下角的取消关注按钮
     */
    private static final By ACTION_CLOSE_BUTTON = By.id("com.xueqiu.android:id/action_close");
    private static final By CANCEL_FOLLOW_BUTTON = By.id("com.xueqiu.android:id/cancel_follow");

    private static final By STOCK_LIST = By.xpath("//*[@resource-id=\"com.xueqiu.android:id/portfolio_list\"]//*[@resource-id=\"com.xueqiu.android:id/container\"]");
    private static final By STOCK_CHECK = By.xpath("//*[@resource-id=\"com.xueqiu.android:id/check\"]");

    private static final By CONFIRM_BUTTON = By.xpath("//*[@resource-id=\"com.xueqiu.android:id/tv_right\" and @text=\"确定\"]");

    public QuotationEditPage(BasePage currentPage) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        wait.until(ExpectedConditions.elementToBeClickable(ACTION_CLOSE_BUTTON));
        prePage = currentPage;
    }

    BasePage finishEdit() {
        click(ACTION_CLOSE_BUTTON);
        return prePage;
    }

    QuotationEditPage unFollowAllStocks() {
        List<MobileElement> list = driver.findElements(STOCK_LIST);
        if (!list.isEmpty()) {
            /*
            1. 勾选全部股票
            2. 点击取消关注
            3. 点击确定
             */
            list.forEach(e -> click(e.findElement(STOCK_CHECK)));
            click(CANCEL_FOLLOW_BUTTON);
            click(CONFIRM_BUTTON);
        }
        return this;
    }
}
