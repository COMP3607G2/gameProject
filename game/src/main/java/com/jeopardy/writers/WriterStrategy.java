package com.jeopardy.writers;

import java.util.ArrayList;

/**
 * Strategy Interface
 */
public interface WriterStrategy{
    /**
    * Write to files
    * @param statements statements
    */
    public void write(ArrayList<String> statements);
}