package com.testcase;

import org.junit.Assert;
import org.junit.Test;

public class BuyTest extends BaseTest {
    @Test
    public void buy() {
        Assert.assertEquals("success", data.get("login"));
    }
}
