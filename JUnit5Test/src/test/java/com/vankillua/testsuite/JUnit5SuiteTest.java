package com.vankillua.testsuite;

import com.vankillua.testcase.LoginTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({LoginTest.class})
public class JUnit5SuiteTest {
}
