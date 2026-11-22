package com.example.orders;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.example.orders.util.ReportWriter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReportWriterTest {

    @TempDir
    File dir;

    /** Reads as UTF-8. Only passes if the platform default charset is UTF-8 (set -Dfile.encoding on 17). */
    @Test
    void writesNonAsciiText() throws Exception {
        File f = new File(dir, "report.csv");
        new ReportWriter(f).writeLine("Zoë Müller,₹100");
        String content = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
        assertTrue(content.contains("Zoë Müller,₹100"));
    }
}
