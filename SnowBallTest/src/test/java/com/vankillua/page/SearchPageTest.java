package com.vankillua.page;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author KILLUA
 * @Date 2020/6/3 20:48
 * @Description
 */
public class SearchPageTest {
    static MainPage mainPage;
    static SearchPage searchPage;

    @BeforeAll
    static void beforeAll() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        mainPage = new MainPage();
        searchPage = mainPage.toSearchPage();
    }

    @Test
    void search() {

    }

    @Test
    void getStockPrice() {

    }
}
