package com.jeopardy;

public class ScoreDisplay implements Observer{
    private String owner;

    public ScoreDisplay(String owner){
        this.owner = owner;
    }

    public void update(String alert){
        System.out.println("\n" + owner + "'s Display: " + alert);
    }
}