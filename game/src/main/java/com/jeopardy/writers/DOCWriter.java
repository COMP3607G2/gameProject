package com.jeopardy.writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DOCWriter implements WriterStrategy{
    private String file;
    
    public DOCWriter(String file){
       this.file = file;
   }
    public void write(ArrayList<String> statements){
        for (int i = 0; i < statements.size(); i++){
            try(FileWriter writer = new FileWriter(file, true)){
               writer.write(statements.get(i));
            } catch(IOException e){
                System.err.println("File not found");
                e.printStackTrace();
            }
        } 

        System.out.println("DOC report generated: " + file);
    }
}