package com.testcase;

import org.junit.jupiter.api.*;

@DisplayName("JUnit5演示类")
public class JUnit5Demo1Test {
    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }

//    @Test
    @RepeatedTest(2)
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
        System.out.println("afterAll");
    }
}
