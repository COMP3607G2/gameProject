package com.jeopardy.writers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

public class WriterStrategyTest {

    @Test
    void testTXTWriter(@TempDir Path tempDir) {
        File outputFile = tempDir.resolve("test_output.txt").toFile();
        String outputPath = outputFile.getAbsolutePath();

        TXTWriter writer = new TXTWriter(outputPath);
        ArrayList<String> statements = new ArrayList<>(Arrays.asList("Line 1", "Line 2", "Line 3"));

        writer.write(statements);

        assertTrue(outputFile.exists(), "TXT file should be created");
        assertTrue(outputFile.length() > 0, "TXT file should not be empty");
    }

    @Test
    void testDOCWriter(@TempDir Path tempDir) {
        File outputFile = tempDir.resolve("test_output.doc").toFile();
        String outputPath = outputFile.getAbsolutePath();

        DOCWriter writer = new DOCWriter(outputPath);
        ArrayList<String> statements = new ArrayList<>(Arrays.asList("Line 1", "Line 2", "Line 3"));

        writer.write(statements);

        assertTrue(outputFile.exists(), "DOC file should be created");
        assertTrue(outputFile.length() > 0, "DOC file should not be empty");
    }

    @Test
    void testPDFWriter(@TempDir Path tempDir) {
        File outputFile = tempDir.resolve("test_output.pdf").toFile();
        String outputPath = outputFile.getAbsolutePath();

        PDFWriter writer = new PDFWriter(outputPath);
        ArrayList<String> statements = new ArrayList<>(Arrays.asList("Line 1", "Line 2", "Line 3"));

        writer.write(statements);

        assertTrue(outputFile.exists(), "PDF file should be created");
        assertTrue(outputFile.length() > 0, "PDF file should not be empty");
    }

    @Test
    void testWriterStrategyInterface() {
        WriterStrategy writer = new TXTWriter("dummy.txt");
        assertTrue(writer instanceof WriterStrategy, "TXTWriter should implement WriterStrategy");
    }
}
