package com.jeopardy.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JSONReader implements Reader {
    private String file;
    private ArrayList<Question> questions = new ArrayList<Question>();
    
    public JSONReader(String file) {
        this.file = file;
    }
    
    public ArrayList<Question> read() {
        try (Scanner scan = new Scanner(new File(file))) {
            StringBuilder jsonContent = new StringBuilder();
            while (scan.hasNextLine()) {
                jsonContent.append(scan.nextLine());
            }
            
            String json = jsonContent.toString().trim();
            // Remove opening and closing brackets
            json = json.substring(1, json.length() - 1);
            
            // Split by objects (each question block)
            String[] questionBlocks = json.split("\\},\\s*\\{");
            
            for (String block : questionBlocks) {
                // Clean up the block
                block = block.replace("{", "").replace("}", "");
                
                Question q = new Question();
                
                // Parse each field
                String[] fields = block.split(",(?=\\s*\"\\w+\"\\s*:)");
                
                for (String field : fields) {
                    field = field.trim();
                    
                    if (field.contains("\"Category\"")) {
                        q.setCategory(extractValue(field));
                    } else if (field.contains("\"Value\"")) {
                        q.setValue(Integer.parseInt(extractValue(field)));
                    } else if (field.contains("\"Question\"")) {
                        q.setStatement(extractValue(field));
                    } else if (field.contains("\"Options\"")) {
                        parseOptions(field, q);
                    } else if (field.contains("\"CorrectAnswer\"")) {
                        q.setCorrect(extractValue(field));
                    }
                }
                
                questions.add(q);
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            e.printStackTrace();
        }
        
        return questions;
    }
    
    private String extractValue(String field) {
        // Extract value between quotes after the colon
        int colonIndex = field.indexOf(":");
        String value = field.substring(colonIndex + 1).trim();
        
        // Remove quotes and commas
        value = value.replace("\"", "").replace(",", "").trim();
        
        return value;
    }
    
    private void parseOptions(String optionsBlock, Question q) {
        // Find the options object content
        int startBrace = optionsBlock.indexOf("{");
        int endBrace = optionsBlock.lastIndexOf("}");
        
        if (startBrace != -1 && endBrace != -1) {
            String optionsContent = optionsBlock.substring(startBrace + 1, endBrace);
            String[] options = optionsContent.split(",(?=\\s*\"[A-D]\"\\s*:)");
            
            for (String option : options) {
                option = option.trim();
                
                if (option.contains("\"A\"")) {
                    q.setA(extractValue(option));
                } else if (option.contains("\"B\"")) {
                    q.setB(extractValue(option));
                } else if (option.contains("\"C\"")) {
                    q.setC(extractValue(option));
                } else if (option.contains("\"D\"")) {
                    q.setD(extractValue(option));
                }
            }
        }
    }
}