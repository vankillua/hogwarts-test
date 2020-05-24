package com.testcase;

import org.junit.Assert;
import org.junit.Test;

public class BuyTest extends BaseTest {
    @Test
    public void buy() {
        if ("success".equals(data.get("login"))) {
            System.out.println("登陆成功，允许购买！");
        } else {
            System.out.println("未登陆，不允许购买！");
        }
        Assert.assertEquals("success", data.get("login"));
    }
}
