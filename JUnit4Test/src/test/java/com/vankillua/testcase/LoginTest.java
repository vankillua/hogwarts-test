package com.vankillua.testcase;

import org.junit.Test;

public class LoginTest extends BaseTest {
    @Test
    public void login() {
        data.put("login", "success");
        System.out.println("登录成功！");
    }
}
