package com.vankillua.snowball.page;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author KILLUA
 * @Date 2020/6/6 13:41
 * @Description
 */
@SpringBootTest()
@DisplayName("行情页测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuotationPageTest {
    @Autowired
    private MainPage mainPage;
    @Autowired
    private QuotationPage quotationPage;

    static MainPage staticMainPage;

//    @BeforeAll
//    static void beforeAll() {
//        applicationContext = SpringApplication.run(AppiumApplication.class);
//        mainPage = new MainPage();
//        quotationPage = mainPage.toQuotationPage();
//    }

    @PostConstruct
    void setStaticMainPage() {
        QuotationPageTest.staticMainPage = mainPage;
        mainPage.toQuotationPage();
    }

    @Test
    @Order(1)
    @DisplayName("取消关注全部自选股票")
    void unFollowAllStocks() {
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
    void addThreeStocks(String stockName, String stockCode) {
        quotationPage.toSearchPage().search(stockName).selectSearchResult(stockCode).cancelSearch();
        assertThat("添加自选股票失败", quotationPage.getMyChosenStockCode().contains(stockCode), is(true));
    }

    @AfterAll
    static void afterAll() {
        staticMainPage.quit();
    }
}
