package com.jeopardy;

public class Question implements Component{
    private String category;
    private int value;
    private String statement;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correct;
    private boolean answered = false;
 
    public void setCategory(String category){this.category = category;}
    
    public void setValue(int value){this.value = value;}
    
    public void setStatement(String s){statement = s;}
    
    public void setA(String A){this.optionA = A;}
    
    public void setB(String B){this.optionB = B;}
    
    public void setC(String C){this.optionC = C;}
    
    public void setD(String D){this.optionD = D;}
    
    public void setCorrect(String correct){this.correct = correct;}
    
    public void answered(){answered = true;}
    
    public String getCategory(){return category;}
    
    public String getStatement(){return statement;}
    
    public int getValue(){return value;}
    
    public String getA(){return optionA;}
    
    public String getB(){return optionB;}
    
    public String getC(){return optionC;}
    
    public String getD(){return optionD;}
    
    public String getCorrect(){return correct;}

    public boolean ifAnswered(){return answered;}

    public String getAnswer(String answer){
        if (answer.equals("A")){
            return optionA;
        }
        else if (answer.equals("B")){
            return optionB;
        }
        else if (answer.equals("C")){
            return optionC;
        }
        else if (answer.equals("D")){
            return optionD;
        }
        return null;
    }
}