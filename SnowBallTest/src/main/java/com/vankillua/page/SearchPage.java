package com.vankillua.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author KILLUA
 * @Date 2020/6/3 20:36
 * @Description
 */
public class SearchPage extends BasePage {
    private BasePage prePage;

    /**
     * 搜索页上方搜索输入栏
     * 搜索页上方取消搜索按钮
     */
    private static final By SEARCH_INPUT_TEXT = By.id("search_input_text");
    private static final By CANCEL_SEARCH_BUTTON = By.id("action_close");

    /**
     * 搜索结果列表
     */
    private static final By SEARCH_RESULT_LIST = By.id("com.xueqiu.android:id/listview");
    private static final String SEARCH_RESULT = "//*[(@resource-id=\"com.xueqiu.android:id/name\" and @text=\"%s\") or (@resource-id=\"com.xueqiu.android:id/code\" and @text=\"%s\")]";
    private static final String SEARCH_RESULT_FOLLOW = "//*[(@resource-id=\"com.xueqiu.android:id/stockName\" and @text=\"%s\") or (@resource-id=\"com.xueqiu.android:id/stockCode\" and @text=\"%s\")]/ancestor::*[@resource-id=\"com.xueqiu.android:id/stock_layout\"]/following-sibling::*//*[@resource-id=\"com.xueqiu.android:id/follow_btn\"]";

    private SearchPage() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT_TEXT));
    }

    public SearchPage(BasePage currentPage) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this();
        prePage = currentPage;
    }

    SearchPage search(String keyword) {
        sendKeys(SEARCH_INPUT_TEXT, keyword);
        return this;
    }

    BasePage cancelSearch() {
        click(CANCEL_SEARCH_BUTTON);
        return prePage;
    }

    SearchPage selectSearchResult(String nameOrCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_RESULT_LIST));
        click(String.format(SEARCH_RESULT, nameOrCode, nameOrCode));
        if (isExists(String.format(SEARCH_RESULT_FOLLOW, nameOrCode, nameOrCode), 2)) {
            click(String.format(SEARCH_RESULT_FOLLOW, nameOrCode, nameOrCode));
        }
        return this;
    }
}
