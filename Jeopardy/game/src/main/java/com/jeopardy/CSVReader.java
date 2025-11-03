package com.jeopardy;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CSVReader implements Reader{
   private String file;
   private ArrayList<Question> questions = new ArrayList<Question>();
 
   public CSVReader(String file){
       this.file = file;
   }
   
   public ArrayList<Question> read(){
       try(Scanner scan = new Scanner(new File(file))){
           if (scan.hasNextLine()){
               scan.nextLine();
           }
           while (scan.hasNextLine()){
               String statement = scan.nextLine();
               String[] s = statement.split(",");
               Question q = new Question();
               q.setCategory(s[0]);
               q.setValue(Integer.parseInt(s[1]));
               q.setStatement(s[2]);
               q.setA(s[3]);
               q.setB(s[4]);
               q.setC(s[5]);
               q.setD(s[6]);
               q.setCorrect(s[7]);
               questions.add(q);
           }
       } catch(FileNotFoundException e){
           System.err.println("File not found");
           e.printStackTrace();
       }
           
       return questions;
   }
}