package com.testcase;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertionTest {
    @Test
    void assertAllTest() {
        assertAll("demo assertAll",
                () -> assertEquals(1, 2),
                () -> assertEquals(1, 1),
                () -> assertEquals(1, 3));
    }
}
