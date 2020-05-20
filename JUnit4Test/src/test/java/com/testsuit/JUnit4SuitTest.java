package com.testsuit;

import com.testcase.BuyTest;
import com.testcase.LoginTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTest.class,
        BuyTest.class
})
public class JUnit4SuitTest {
}
