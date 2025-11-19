package com.jeopardy.writers;

import java.util.ArrayList;

public interface WriterStrategy{
    public void write(ArrayList<String> statements);
}