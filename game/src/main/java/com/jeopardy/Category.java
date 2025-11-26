package com.jeopardy;

import java.util.ArrayList;

/**
 * Categorizes questions
 */
public class Category implements Component{
    /**Name of category */
    private String name;
    /**Related questions */
    ArrayList<Question> questions = new ArrayList<Question>();

    /**Creates a category */
    public Category(String name){
        this.name = name;
    }
    
    /**Adds a question to a category
     * @param q a question
     */
    public void add(Question q){
        questions.add(q);
    }

    /**Returns the name of a category
     *@return category's name
     */
    public String getName(){
        return name;
    }

    /**Gets all related questions
     * @return all questions
     */
    public ArrayList<Question> getList(){
        return questions;
    }
    
    /**Checks if all questions were answered
     * @return true if all questions answered, false otherwise
     */
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