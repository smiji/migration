package com.example.orders.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * TRAP 1: cleanup relies on finalize() (deprecated for removal in JDK 18).
 * TRAP 2: FileWriter uses the platform default charset (not UTF-8 until JDK 18).
 */
public class ReportWriter {

    private final Writer writer;

    public ReportWriter(File file) throws IOException {
        this.writer = new FileWriter(file);
    }

    public void writeLine(String line) throws IOException {
        writer.write(line);
        writer.write("\n");
        writer.flush();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            writer.close();
        } finally {
            super.finalize();
        }
    }
}
