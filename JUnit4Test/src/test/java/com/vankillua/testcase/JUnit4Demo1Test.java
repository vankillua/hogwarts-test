package com.vankillua.testcase;

import org.junit.*;

public class JUnit4Demo1Test {
    @BeforeClass
    public static void beforeClass() {
        System.out.println("before class");
    }

    @Before
    public void before() {
        System.out.println("before");
    }

    @Test
    public void func1() {
        System.out.println("func1 test");
    }

    @Test
    public void func2() {
        System.out.println("func2 test");
    }

    @Test
    @Ignore
    public void func3() {
        System.out.println("func3 test");
    }

    @After
    public void after() {
        System.out.println("after");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("after class");
    }
}
