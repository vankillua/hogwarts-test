package com.vankillua.snowball.page;

import com.vankillua.common.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author KILLUA
 * @Date 2020/6/3 20:35
 * @Description
 */
@Component(value = "snowballMainPage")
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

    private SearchPage searchPage;
    private QuotationPage quotationPage;

    @Autowired
    public void setSearchPage(SearchPage searchPage) {
        this.searchPage = searchPage;
    }

    @Autowired
    public void setQuotationPage(QuotationPage quotationPage) {
        this.quotationPage = quotationPage;
    }

//    public MainPage() {
//        new WebDriverWait(driver, LOAD_MAIN_PAGE_TIMEOUT).until(ExpectedConditions.elementToBeClickable(HOME_SEARCH));
//    }

//    @PostConstruct
//    void waitMainPage() {
//        new WebDriverWait(driver, LOAD_MAIN_PAGE_TIMEOUT).until(ExpectedConditions.elementToBeClickable(HOME_SEARCH));
//    }

    public SearchPage toSearchPage() {
        click(HOME_SEARCH);
        return searchPage.setPrePage(this);
    }

    public QuotationPage toQuotationPage() {
        click(QUOTATION_TAB);
        return quotationPage.setPrePage(this);
    }

}
