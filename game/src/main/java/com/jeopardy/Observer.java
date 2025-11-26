package com.jeopardy;

/**
 * Creates Observer
 */
public interface Observer {
    /**
    * Respond to a change
    * @param alert change message
    */
    public void update(String alert);
}