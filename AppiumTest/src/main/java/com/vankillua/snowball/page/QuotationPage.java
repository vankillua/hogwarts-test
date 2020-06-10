package com.vankillua.snowball.page;

import com.vankillua.common.BasePage;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author KILLUA
 * @Date 2020/6/6 10:42
 * @Description
 */
@Component
public class QuotationPage extends BasePage {
    private BasePage prePage;

    /**
     * 行情页上方搜索按钮（放大镜图标）
     * 行情页编辑按钮（管理自选股和分组）
     */
    private static final By ACTION_SEARCH_BUTTON = By.id("action_search");
    private static final By EDIT_BUTTON = By.id("edit_group");

    private static final By STOCK_LIST = By.xpath("//*[@resource-id=\"com.xueqiu.android:id/content_recycler\"]//*[@resource-id=\"com.xueqiu.android:id/row_layout\"]");
    private static final By STOCK_CODE = By.id("portfolio_stockCode");

    private SearchPage searchPage;
    private QuotationEditPage quotationEditPage;

    @Autowired
    public void setSearchPage(SearchPage searchPage) {
        this.searchPage = searchPage;
    }

    @Autowired
    public void setQuotationEditPage(QuotationEditPage quotationEditPage) {
        this.quotationEditPage = quotationEditPage;
    }

//    public QuotationPage() {
//        wait.until(ExpectedConditions.elementToBeClickable(ACTION_SEARCH_BUTTON));
//    }

    QuotationPage setPrePage(BasePage currentPage) {
        wait.until(ExpectedConditions.elementToBeClickable(ACTION_SEARCH_BUTTON));
        prePage = currentPage;
        return this;
    }

    SearchPage toSearchPage() {
        click(ACTION_SEARCH_BUTTON);
        return searchPage.setPrePage(this);
    }

    QuotationEditPage toEditPage() {
        click(EDIT_BUTTON);
        return quotationEditPage.setPrePage(this);
    }

    List<MobileElement> getMyChosenStocks() {
        return driver.findElements(STOCK_LIST);
    }

    List<String> getMyChosenStockCode() {
        return getMyChosenStocks().stream()
                .filter(Objects::nonNull)
                .map(e -> e.findElement(STOCK_CODE))
                .filter(Objects::nonNull)
                .map(MobileElement::getText)
                .collect(Collectors.toList());
    }
}
