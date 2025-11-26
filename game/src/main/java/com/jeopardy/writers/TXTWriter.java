package com.jeopardy.writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Writes game summary as a TXT file
 */
public class TXTWriter implements WriterStrategy{
    /**The file path that is written to */
    private String file;
    
    /**Creates a TXTWriter */
    public TXTWriter(String file){
       this.file = file;
    }
    
    /**
    * Writes the game summary to TXT file
    * @param statements all lines in summary report
    */
    @Override
    public void write(ArrayList<String> statements){
        for (int i = 0; i < statements.size(); i++){
            try(FileWriter writer = new FileWriter(file, true)){
                writer.write(statements.get(i));
            }catch(IOException e){
                System.err.println("File not found");
                e.printStackTrace();
            }
        }
        
        System.out.println("TXT report generated: " + file);
    }
}