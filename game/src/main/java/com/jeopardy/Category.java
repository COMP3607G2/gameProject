package com.jeopardy;

import java.util.ArrayList;

public class Category implements Component{
    private String name;
    ArrayList<Question> questions = new ArrayList<Question>();

    public Category(String name){
        this.name = name;
    }
    
    public void add(Question q){
        questions.add(q);
    }

    public String getName(){
        return name;
    }

    public ArrayList<Question> getList(){
        return questions;
    }
    
    @Override
    public boolean ifAnswered(){
        boolean ans = true;
        for (Question q : questions){
            if (!q.ifAnswered()){
                ans = false;
            }
        }
        return ans;
    }
}