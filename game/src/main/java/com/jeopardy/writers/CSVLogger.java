package com.jeopardy.writers;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.io.File;

public class CSVLogger{
    private String caseID;
    private String file;
    private static int number = 1;
    
    public CSVLogger(String file){
        this.file = file;
        caseID = "GAME" + number;
        number++;
    }
    
    public void logger(String playerID, String activity, String category, String questionValue, String answerGiven, String result, String scoreAfterPlay){
            File f = new File(file);
            try(FileWriter writer = new FileWriter(f, true)){
                if (!f.exists() || f.length() == 0){
                    writer.write("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play\n");
                }
               else{
                    LocalDateTime obj = LocalDateTime.now();
                    String statement =  this.caseID + "," + playerID + "," + activity + "," + obj + "," + category + "," + questionValue + "," + answerGiven + "," + result + "," + scoreAfterPlay + "\n";
                    writer.write(statement);
               }
               
           } catch(IOException e){
           System.err.println("File not found");
           e.printStackTrace();
            }
    }

}
