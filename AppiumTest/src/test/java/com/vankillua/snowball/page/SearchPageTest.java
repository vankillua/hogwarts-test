package com.vankillua.snowball.page;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;

/**
 * @Author KILLUA
 * @Date 2020/6/3 20:48
 * @Description
 */
@SpringBootTest()
@DisplayName("搜索页测试")
public class SearchPageTest {
    @Autowired
    private MainPage mainPage;
    @Autowired
    private SearchPage searchPage;

    static MainPage staticMainPage;

//    @BeforeAll
//    static void beforeAll() {
//        mainPage = new MainPage();
//        searchPage = mainPage.toSearchPage();
//    }

    @PostConstruct
    void setStaticMainPage() {
        SearchPageTest.staticMainPage = mainPage;
        mainPage.toSearchPage();
    }

    @Test
    void search() {

    }

    @Test
    void getStockPrice() {

    }

    @AfterEach
    void afterEach() {
        searchPage.cancelSearch();
    }

    @AfterAll
    static void afterAll() {
        staticMainPage.quit();
    }
}
