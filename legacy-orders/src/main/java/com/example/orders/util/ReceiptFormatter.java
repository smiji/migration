package com.example.orders.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public final class ReceiptFormatter {

    private ReceiptFormatter() {
    }

    /**
     * TRAP: output differs between JDK 8 (legacy locale data) and JDK 9+ (CLDR).
     * Java 8: "Nov 20, 2026 3:45:00 PM"
     * Java 17: "Nov 20, 2026, 3:45:00 PM"
     */
    public static String format(LocalDateTime time) {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.US)
                .format(time);
    }
}
