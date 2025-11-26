package com.jeopardy;

import java.util.ArrayList;
import com.jeopardy.writers.WriterStrategy;

public class WriterContext {
    private WriterStrategy strategy;

    public void setWriterStrategy(WriterStrategy strategy){
        this.strategy = strategy;
    }

    public void list(ArrayList<String> statements){
        strategy.write(statements);
    }
}