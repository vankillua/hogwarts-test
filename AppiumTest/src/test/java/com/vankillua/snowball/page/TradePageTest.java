package com.vankillua.snowball.page;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author KILLUA
 * @Date 2020/7/2 16:54
 * @Description
 */
@SpringBootTest
@DisplayName("交易页测试")
class TradePageTest {
    private static final Logger logger = LoggerFactory.getLogger(TradePageTest.class);

    @Autowired
    private MainPage mainPage;
    @Autowired
    private TradePage tradePage;

    static MainPage staticMainPage;

    @PostConstruct
    void setStaticMainPage() {
        TradePageTest.staticMainPage = mainPage;
        mainPage.waitForPage().toTradePage();
    }

    @Test
    void openAStockAccount() {
        tradePage.openAStockAccount();
    }

    @AfterAll
    static void afterAll() {
        staticMainPage.quit();
    }
}