package com.example.orders.util;

import sun.misc.BASE64Encoder;   // TRAP: internal API, removed in JDK 9

public final class Base64Util {

    private Base64Util() {
    }

    public static String enc(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }
}
