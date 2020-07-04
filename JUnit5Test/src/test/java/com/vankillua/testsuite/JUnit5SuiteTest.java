package com.vankillua.testsuite;

import com.vankillua.testcase.JUnit5Demo1Test;
import com.vankillua.testcase.LoginTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({LoginTest.class, JUnit5Demo1Test.class})
public class JUnit5SuiteTest {
}
