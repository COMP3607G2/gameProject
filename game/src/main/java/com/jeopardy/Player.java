package com.jeopardy;

import java.util.ArrayList;

/**
 * Player information and actions
 */
public class Player implements Subject{
    /**Name of player */
    private String name;
    /**Number of points */
    private int points = 0;
    /**Observers attached to player */
    private ArrayList<Observer> observers = new ArrayList<>();

    /**
    * Creates a player
    * @param name name of player
    */
    public Player(String name){this.name = name;}
    
    /**
    * Gets player's name
    * @return player's name
    */
    public String getName(){return name;}
    
    /**
    * Gets player's points
    * @return player's points
    */
    public int getPoints(){return points;}
    
    /**
    * Updates player's points
    * @param value points earned
    */
    public void updatePoints(int value){
        this.points+=value;
        notify(name + "'s points has been updated to " + points + " points.");
    }

    /**
    * Add observer to player
    * @param observer observer created
    */
    @Override
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    /**
    * Remove observer from player
    * @param observer observer removed
    */
    @Override
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    /**
    * Notify all observers when points are changed
    * @param alert alert to be sent
    */
    @Override
    public void notify(String alert){
        for (Observer observer : observers){
            observer.update(alert);
        } 
    }
}