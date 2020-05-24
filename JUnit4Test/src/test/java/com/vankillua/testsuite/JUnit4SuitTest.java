package com.vankillua.testsuite;

import com.vankillua.testcase.BuyTest;
import com.vankillua.testcase.LoginTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTest.class,
        BuyTest.class
})
public class JUnit4SuitTest {
}
