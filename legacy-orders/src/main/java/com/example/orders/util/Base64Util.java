package com.example.orders.util;

   // TRAP: internal API, removed in JDK 9
import java.util.Base64;
public final class Base64Util {

    private Base64Util() {
    }

    public static String enc(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
