package com.example.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import com.example.orders.util.ReceiptFormatter;

import org.junit.jupiter.api.Test;

class ReceiptFormatterTest {

    /** Passes on Java 8. EXPECTED TO FAIL on Java 17 (CLDR adds a comma). Fix in Phase 2. */
    @Test
    void formatsMediumDateTime() {
        String s = ReceiptFormatter.format(LocalDateTime.of(2026, 11, 20, 15, 45, 0));
        assertEquals("Nov 20, 2026 3:45:00 PM", s);
    }
}
