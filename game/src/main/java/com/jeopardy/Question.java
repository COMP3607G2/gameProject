package com.jeopardy;

/**
 * Question information and actions
 */
public class Question implements Component{
    /**Name of category */
    private String category;
    /**Question value*/
    private int value;
    /**Question to be asked */
    private String statement;
    /**Option A */
    private String optionA;
    /**Option B */
    private String optionB;
    /**Option C */
    private String optionC;
    /**Option D */
    private String optionD;
    /**Correct option*/
    private String correct;
    /**If the questions was answered or not */
    private boolean answered = false;
 
    /**
    * Sets category
    * @param category category name
    */
    public void setCategory(String category){this.category = category;}
    
    /**
    * Sets question value
    * @param value question value
    */
    public void setValue(int value){this.value = value;}
    
    /**
    * Sets question statement
    * @param s question statement
    */
    public void setStatement(String s){statement = s;}
    
    /**
    * Sets option A
    * @param A option A
    */
    public void setA(String A){this.optionA = A;}
    
    /**
    * Sets option B
    * @param B option B
    */
    public void setB(String B){this.optionB = B;}
    
    /**
    * Sets option C
    * @param C option C
    */
    public void setC(String C){this.optionC = C;}
    
    /**
    * Sets option D
    * @param D option D
    */
    public void setD(String D){this.optionD = D;}
    
    /**
    * Sets correct option
    * @param correct correct option
    */
    public void setCorrect(String correct){this.correct = correct;}
    
    /**
    * Sets that question was answered
    */
    public void answered(){answered = true;}
    
    /**
    * Returns question's category
    * @return category
    */
    public String getCategory(){return category;}
    
    /**
    * Returns question's statement
    * @return statement
    */
    public String getStatement(){return statement;}
    
    /**
    * Returns question's value
    * @return value
    */
    public int getValue(){return value;}
    
    /**
    * Returns option A
    * @return option A
    */
    public String getA(){return optionA;}
    
    /**
    * Returns option B
    * @return option B
    */
    public String getB(){return optionB;}
    
    /**
    * Returns option C
    * @return option C
    */
    public String getC(){return optionC;}
    
    /**
    * Returns option D
    * @return option D
    */
    public String getD(){return optionD;}
    
    /**
    * Returns correct option
    * @return correct option
    */
    public String getCorrect(){return correct;}

    /**
    * Returns if question was answered
    * @return true if question was answered, false otherwise
    */
    @Override
    public boolean ifAnswered(){return answered;}

    /**
    * Returns answer
    * @param answer correct answer
    */
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