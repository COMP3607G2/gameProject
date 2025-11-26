package com.jeopardy.readers;

import java.util.ArrayList;
import com.jeopardy.Question;

/**
 * Reads different files for Questions
 */
public interface Reader {
    /**
    * Reads the questions from a file
    * @return all questions
    */
    public ArrayList<Question> read();
}