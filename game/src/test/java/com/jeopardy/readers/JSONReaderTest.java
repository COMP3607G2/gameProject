package com.jeopardy.readers;

import com.jeopardy.Component;
import com.jeopardy.Question;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class JSONReaderTest {

    @Test
    void testReadValidJSONFile() {
        String testFile = getClass().getResource("/sample_game_JSON.json").getFile();
        JSONReader reader = new JSONReader(testFile);

        ArrayList<Question> questions = reader.read();

        assertNotNull(questions, "Questions list should not be null");
        assertFalse(questions.isEmpty(), "Questions list should not be empty");
        assertTrue(questions.size() > 0, "Should read multiple questions");

        Question firstQuestion = questions.get(0);
        assertNotNull(firstQuestion.getCategory(), "Category should not be null");
        assertNotNull(firstQuestion.getStatement(), "Statement should not be null");
        assertTrue(firstQuestion.getValue() > 0, "Value should be positive");
        assertNotNull(firstQuestion.getA(), "Option A should not be null");
        assertNotNull(firstQuestion.getB(), "Option B should not be null");
        assertNotNull(firstQuestion.getC(), "Option C should not be null");
        assertNotNull(firstQuestion.getD(), "Option D should not be null");
        assertNotNull(firstQuestion.getCorrect(), "Correct answer should not be null");
    }

    @Test
    void testReaderInterfaceImplementation() {
        JSONReader reader = new JSONReader("test.json");
        assertTrue(reader instanceof Reader, "JSONReader should implement Reader interface");


        ArrayList<Question> questions = reader.read();
        assertNotNull(questions, "read() should return a list (even if empty)");
    }

    @Test
    void testComponentStatusOnLoadedQuestions() {
        String testFile = getClass().getResource("/sample_game_JSON.json").getFile();
        JSONReader reader = new JSONReader(testFile);
        ArrayList<Question> questions = reader.read();

        if (!questions.isEmpty()) {
            for (Question q : questions) {
                assertFalse(q.ifAnswered(), "Newly loaded questions should be unanswered");
                assertTrue(q instanceof Component, "Questions should implement Component");
            }
        }
    }
}
