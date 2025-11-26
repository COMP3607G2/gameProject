package com.jeopardy;

/**
 * Creates Question and Category components
 */
public interface Component {
    /**
    * Checks if component was answered
    * @return true if component was answered, false otherwise
    */
    public boolean ifAnswered();
}