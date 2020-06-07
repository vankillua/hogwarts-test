package com.vankillua.snowball.page;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author KILLUA
 * @Date 2020/6/6 13:41
 * @Description
 */
@DisplayName("行情页测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuotationPageTest {
    static MainPage mainPage;
    static QuotationPage quotationPage;
    static QuotationEditPage quotationEditPage;

    @BeforeAll
    static void beforeAll() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        mainPage = new MainPage();
        quotationPage = mainPage.toQuotationPage();
    }

    @Test
    @Order(1)
    @DisplayName("取消关注全部自选股票")
    void unFollowAllStocks() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        quotationPage.toEditPage().unFollowAllStocks().finishEdit();
        assertThat("取消关注全部自选股票失败", quotationPage.getMyChosenStocks().size(), is(0));
    }

    @ParameterizedTest
    @Order(2)
    @DisplayName("添加三只自选股票")
    @CsvSource({
            "贵州茅台, SH600519",
            "哔哩哔哩, BILI",
            "中国平安, SH601318",
    })
    void addThreeStocks(String stockName, String stockCode) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        quotationPage.toSearchPage().search(stockName).selectSearchResult(stockCode).cancelSearch();
        assertThat("添加自选股票失败", quotationPage.getMyChosenStockCode().contains(stockCode), is(true));
    }

    @AfterAll
    static void afterAll() {
        quotationPage.quit();
    }
}
