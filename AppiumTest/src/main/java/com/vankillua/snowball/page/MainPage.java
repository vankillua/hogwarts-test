package com.vankillua.snowball.page;

import com.vankillua.common.BasePage;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author KILLUA
 * @Date 2020/6/3 20:35
 * @Description
 */
@Component(value = "snowballMainPage")
public class MainPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(MainPage.class);

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

    @Override
    @SuppressWarnings("unchecked")
    protected MainPage waitForPage() {
        if (!isExists(HOME_SEARCH, LOAD_MAIN_PAGE_TIMEOUT)) {
            logger.warn("等待超时，首页仍未加载完成");
        }
        return this;
    }

    @Override
    protected MainPage setPrePage(BasePage currentPage) {
        return this;
    }

    public SearchPage toSearchPage() {
        click(HOME_SEARCH);
        return searchPage.waitForPage().setPrePage(this);
    }

    public QuotationPage toQuotationPage() {
        click(QUOTATION_TAB);
        return quotationPage.waitForPage().setPrePage(this);
    }
}
