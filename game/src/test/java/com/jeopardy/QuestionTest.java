
package com.jeopardy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jeopardy.Question;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    private Question question;
    
    @BeforeEach
    void setUp() {
        question = new Question();
        question.setCategory("Arrays");
        question.setValue(100);
        question.setStatement("What declears an array of 5 integers?");
        question.setA("int arr[5];");
        question.setB("array<List> arr;");
        question.setC("int arr;");
        question.setD("int arr(5);");
        question.setCorrect("A");
    }

    @Test
    void testQuestionCreationandGetters(){
        assertEquals("Arrays", question.getCategory());
        assertEquals(100, question.getValue());
        assertEquals("What declears an array of 5 integers?", question.getStatement());
        assertEquals("int arr[5];", question.getA());
        assertEquals("array<List> arr;", question.getB());
        assertEquals("int arr;", question.getC());
        assertEquals("int arr(5);", question.getD());
        assertEquals("A", question.getCorrect());
    }

    @Test
    void testAnswerStatus(){
        assertFalse(question.ifAnswered(), "Question starts as unanswered.");
        question.answered();
        assertTrue(question.ifAnswered(), "Question should be marked as answered.");
    }

    @Test
    void testGetAnswerByChoice(){
        assertEquals("int arr[5];", question.getAnswer("A"));
        assertEquals("array<List> arr;", question.getAnswer("B"));
        assertEquals("int arr;", question.getAnswer("C"));
        assertEquals("int arr(5);", question.getAnswer("D"));
        assertNull(question.getAnswer("E"), "Invalid choice should return null.");
    }

    @Test
    void testQuestiomWithNullValues(){
        Question nullQ = new Question();
        assertNull(nullQ.getCategory());
        assertEquals(0, nullQ.getValue());
        assertNull(nullQ.getStatement());
        assertNull(nullQ.getCorrect());
        assertFalse(nullQ.ifAnswered());
    }
}