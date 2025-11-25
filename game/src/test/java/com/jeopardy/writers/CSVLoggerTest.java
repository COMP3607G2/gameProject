package com.jeopardy.writers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.io.IOException;

public class CSVLoggerTest {

    @Test
    void testLoggerCreatesFileAndWritesHeader(@TempDir Path tempDir) throws IOException {
        File logFile = tempDir.resolve("game_log.csv").toFile();
        String logPath = logFile.getAbsolutePath();

        CSVLogger logger = new CSVLogger(logPath);
        logger.logger("Player1", "Answer", "Science", "100", "What is Java?", "Correct", "100");

        assertTrue(logFile.exists(), "Log file should be created");

        List<String> lines = Files.readAllLines(logFile.toPath());
        assertFalse(lines.isEmpty(), "Log file should not be empty");

        assertEquals(
                "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play",
                lines.get(0));

        assertTrue(lines.size() >= 2, "Should have at least header and one log entry");
        String entry = lines.get(1);
        assertTrue(entry.contains("Player1"));
        assertTrue(entry.contains("Science"));
        assertTrue(entry.contains("Correct"));
    }

    @Test
    void testLoggerAppendsToExistingFile(@TempDir Path tempDir) throws IOException {
        File logFile = tempDir.resolve("game_log.csv").toFile();
        String logPath = logFile.getAbsolutePath();

        CSVLogger logger1 = new CSVLogger(logPath);
        logger1.logger("Player1", "Start", "N/A", "0", "N/A", "N/A", "0");

        CSVLogger logger2 = new CSVLogger(logPath);
        logger2.logger("Player1", "Answer", "History", "200", "Who is ...", "Incorrect", "-200");

        List<String> lines = Files.readAllLines(logFile.toPath());

        assertEquals(3, lines.size(), "Should have header and 2 entries");
        assertEquals(
                "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play",
                lines.get(0));
        assertTrue(lines.get(1).contains("Start"));
        assertTrue(lines.get(2).contains("History"));
    }
}
