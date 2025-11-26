package com.jeopardy.readers;

import java.util.Scanner;

import com.jeopardy.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Reads CSV files for Questions
 */
public class CSVReader implements Reader{
   /**The file path that is read */
   private String file;
   /**The questions that would be used in the game */
   private ArrayList<Question> questions = new ArrayList<Question>();
 
   /**
    * Creates a CSVReader
    * @param file file to be read from
    */
   public CSVReader(String file){
       this.file = file;
   }
   
   /**
    * Reads the questions from CSV file
    * @return all questions
    */
   @Override
   public ArrayList<Question> read(){
       try(Scanner scan = new Scanner(new File(file))){
           if (scan.hasNextLine()){
               scan.nextLine();
           }
           while (scan.hasNextLine()){
               String statement = scan.nextLine();
               String[] s = statement.split(",");
               Question q = new Question();
               q.setCategory(s[0].trim());
               q.setValue(Integer.parseInt(s[1].trim()));
               q.setStatement(s[2].trim());
               q.setA(s[3].trim());
               q.setB(s[4].trim());
               q.setC(s[5].trim());
               q.setD(s[6].trim());
               q.setCorrect(s[7].trim());
               questions.add(q);
           }
       } catch(FileNotFoundException e){
           System.err.println("File not found");
           e.printStackTrace();
       }    
       return questions;
   }
}