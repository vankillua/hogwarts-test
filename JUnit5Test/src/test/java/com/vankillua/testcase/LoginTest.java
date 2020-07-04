package com.vankillua.testcase;

import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LoginTest {
    static HashMap<String, String> data = new HashMap<String, String>();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    static void beforeAll() {
        System.out.println("LoginTest before all: " + sdf.format(new Date()));
    }

    @Test
    void login() {
        data.put("login", "success");
        System.out.println("登陆成功！");
    }

    @Nested
    class PayTest {
        @Test
        void pay() {
            if (data.containsKey("buy") && "success".equals(data.get("buy"))) {
                System.out.println("允许购买，准备支付！");
            } else {
                System.out.println("不允许购买，退出支付！");
            }
            Assertions.assertEquals("success", data.getOrDefault("buy", "failure"));
        }
    }

    @Nested
    class BuyTest {
        @Test
        void buy() {
            if ("success".equals(data.get("login"))) {
                System.out.println("登陆成功，允许购买！");
                data.put("buy", "success");
            } else {
                System.out.println("未登陆，不允许购买！");
                data.put("buy", "failure");
            }
            Assertions.assertEquals("success", data.get("login"));
        }
    }

    @AfterAll
    static void afterAll() {
        System.out.println("LoginTest after all: " + sdf.format(new Date()));
    }
}
