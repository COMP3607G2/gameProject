package com.jeopardy.readers;

import com.jeopardy.Component;
import com.jeopardy.Question;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class CSVReaderTest {
    
    @Test
    void testReadValidCSVFile() {
        String testFile = getClass().getResource("/sample_game_CSV.csv").getFile();
        CSVReader reader = new CSVReader(testFile);
        
        ArrayList<Question> questions = reader.read();
        
        assertNotNull(questions, "Questions list should not be null");
        assertFalse(questions.isEmpty(), "Questions list should not be empty");
        assertTrue(questions.size() > 0, "Should read multiple questions");
        
        Question firstQuestion = questions.get(0);
        assertNotNull(firstQuestion.getCategory());
        assertNotNull(firstQuestion.getStatement());
        assertTrue(firstQuestion.getValue() > 0);
        assertNotNull(firstQuestion.getA());
        assertNotNull(firstQuestion.getB());
        assertNotNull(firstQuestion.getC());
        assertNotNull(firstQuestion.getD());
        assertNotNull(firstQuestion.getCorrect());
        
        assertEquals("Variables & Data Types", firstQuestion.getCategory());
        assertEquals(100, firstQuestion.getValue());
    }  
    
    @Test
    void testReaderInterfaceImplementation() {
        CSVReader reader = new CSVReader("test.csv");
        assertTrue(reader instanceof Reader, "CSVReader should implement Reader interface");
        
        ArrayList<Question> questions = reader.read();
        assertNotNull(questions, "read() should return a list (even if empty)");
    }
    
    @Test
    void testComponentStatusOnLoadedQuestions() {
        String testFile = getClass().getResource("/sample_game_CSV.csv").getFile();
        CSVReader reader = new CSVReader(testFile);
        ArrayList<Question> questions = reader.read();
        
        for (Question q : questions) {
            assertFalse(q.ifAnswered(), "Newly loaded questions should be unanswered");
            assertTrue(q instanceof Component, "Questions should implement Component");
        }
    }
}
