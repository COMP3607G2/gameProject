package com.jeopardy.readers;

import java.util.ArrayList;

import com.jeopardy.Question;

public interface Reader {
    public ArrayList<Question> read();
}