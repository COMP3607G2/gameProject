package com.jeopardy;

import java.util.ArrayList;
import com.jeopardy.writers.WriterStrategy;

/**
 * Context for Writer Strategy
 */
public class WriterContext {
    /**Strategy chosen */
    private WriterStrategy strategy;

    /**
    * Sets strategy
    * @param strategy strategy chosen
    */
    public void setWriterStrategy(WriterStrategy strategy){
        this.strategy = strategy;
    }

    /**
    * Uses strategy
    * @param statements strategy chosen
    */
    public void list(ArrayList<String> statements){
        strategy.write(statements);
    }
}