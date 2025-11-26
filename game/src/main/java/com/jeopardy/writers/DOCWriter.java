package com.jeopardy.writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Writes game summary as a DOC file
 */
public class DOCWriter implements WriterStrategy{
    /**The file path that is written to */
    private String file;

    /**
    * Writes game summary as a DOC file
    * @param file file to be written to
    */
    public DOCWriter(String file){
       this.file = file;
    }
    
    /**
    * Writes the game summary to DOC file
    * @param statements all lines in summary report
    */
    @Override
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