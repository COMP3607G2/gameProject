package com.jeopardy;

/**
 * Creates Subject
 */
public interface Subject {
    /**
    * Add observer to player
    * @param observer observer created
    */
    public void addObserver(Observer observer);

    /**
    * Remove observer from player
    * @param observer observer removed
    */
    public void removeObserver(Observer observer);

    /**
    * Notify observer when points are changed
    * @param alert alert to be sent
    */
    public void notify(String alert);
}