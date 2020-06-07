package com.vankillua.snowball.page;

import com.vankillua.common.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author KILLUA
 * @Date 2020/6/3 20:35
 * @Description
 */
public class MainPage extends BasePage {
    private static final long LOAD_MAIN_PAGE_TIMEOUT = 30L;

    /**
     * 首页上方搜索栏
     */
    private static final By HOME_SEARCH = By.id("home_search");

    /**
     * 首页下方tab按钮（雪球、行情、交易、我的）
     */
    private static final By QUOTATION_TAB = By.xpath("//*[@text=\"行情\"]");
    private static final By TRADE_TAB = By.xpath("//*[@text=\"交易\"]");

    public MainPage() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super();
        new WebDriverWait(driver, LOAD_MAIN_PAGE_TIMEOUT).until(ExpectedConditions.elementToBeClickable(HOME_SEARCH));
    }

    public SearchPage toSearchPage() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        click(HOME_SEARCH);
        return new SearchPage(this);
    }

    public QuotationPage toQuotationPage() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        click(QUOTATION_TAB);
        return new QuotationPage();
    }
}
