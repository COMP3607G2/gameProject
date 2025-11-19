package com.jeopardy;

import java.util.ArrayList;

public class Player implements Subject{
    private String name;
    private int points = 0;
    private ArrayList<Observer> observers = new ArrayList<>();

    public Player(String name){this.name = name;}
    
    public String getName(){return name;}
    
    public int getPoints(){return points;}
    
    public void updatePoints(int value){
        this.points+=value;
        notify(name + "'s points has been updated to " + points + " points.");
    }

    @Override
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer listener){
        observers.remove(listener);
    }

    @Override
    public void notify(String alert){
        for (Observer observer : observers){
            observer.update(alert);
        } 
    }
}