package com.example.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.orders.util.Base64Util;

import org.junit.jupiter.api.Test;

class Base64UtilTest {

    @Test
    void encodes() {
        assertEquals("YWJj", Base64Util.enc("abc".getBytes()));
    }
}
