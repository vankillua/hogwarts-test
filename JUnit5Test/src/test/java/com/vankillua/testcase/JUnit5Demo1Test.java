package com.vankillua.testcase;

import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@DisplayName("JUnit5演示类")
public class JUnit5Demo1Test {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll: " + sdf.format(new Date()));
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }

    @Test
//    @RepeatedTest(2)
    @DisplayName("func1 测试方法")
    void func1() {
        System.out.println("func1");
    }

    @Test
    @Disabled
    @DisplayName("func2 测试方法")
    void func2() {
        System.out.println("func2");
    }

    @AfterEach
    void afterEach() {
        System.out.println("afterEach");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll: " + sdf.format(new Date()));
    }
}
