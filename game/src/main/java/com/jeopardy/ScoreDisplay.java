package com.jeopardy;

/**
 * Display for changes
 * 
 */
public class ScoreDisplay implements Observer{
    /**Name of owner */
    private String owner;

    /**
    * Creates display
    * @param owner owner
    */
    public ScoreDisplay(String owner){
        this.owner = owner;
    }

    /**
    * Updates with message
    * @param alert updates with alert message
    */
    @Override
    public void update(String alert){
        System.out.println("\n" + owner + "'s Display: " + alert);
    }
}