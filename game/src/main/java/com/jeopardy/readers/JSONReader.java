package com.jeopardy.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import com.jeopardy.Question;

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
            
            // **CHANGE 1: Better bracket handling**
            if (json.startsWith("[") && json.endsWith("]")) {
                json = json.substring(1, json.length() - 1).trim();
            }
            
            // **CHANGE 2: More reliable splitting**
            String[] questionBlocks = json.split("\\}\\s*,\\s*\\{");
            
            for (String block : questionBlocks) {
                // **CHANGE 3: Ensure proper JSON object format**
                if (!block.startsWith("{")) block = "{" + block;
                if (!block.endsWith("}")) block = block + "}";
                
                Question q = parseQuestionBlock(block);
                if (q != null) questions.add(q);
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("JSON file not found: " + file);
        }
        
        return questions;
    }
    
    private Question parseQuestionBlock(String block) {
        try {
            Question q = new Question();
            
            // **CHANGE 4: Use improved value extraction**
            q.setCategory(extractJsonValue(block, "Category"));
            q.setStatement(extractJsonValue(block, "Question"));
            
            String valueStr = extractJsonValue(block, "Value");
            q.setValue(valueStr.isEmpty() ? 0 : Integer.parseInt(valueStr));
            
            q.setCorrect(extractJsonValue(block, "CorrectAnswer"));
            
            // **CHANGE 5: Parse options with better logic**
            parseOptions(block, q);
            
            return q;
            
        } catch (Exception e) {
            System.err.println("Error parsing question: " + e.getMessage());
            return null;
        }
    }
    
    // **CHANGE 6: NEW METHOD - Better JSON value extraction**
    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex == -1) return "";
        
        int valueStart = json.indexOf(":", keyIndex) + 1;
        if (valueStart <= keyIndex) return "";
        
        // Skip whitespace
        while (valueStart < json.length() && Character.isWhitespace(json.charAt(valueStart))) {
            valueStart++;
        }
        
        if (valueStart >= json.length()) return "";
        
        // Handle quoted values
        if (json.charAt(valueStart) == '"') {
            valueStart++; // Skip opening quote
            int valueEnd = json.indexOf("\"", valueStart);
            if (valueEnd == -1) return "";
            return json.substring(valueStart, valueEnd).trim();
        }
        
        // Handle unquoted values
        int valueEnd = valueStart;
        while (valueEnd < json.length() && 
               json.charAt(valueEnd) != ',' && 
               json.charAt(valueEnd) != '}' &&
               !Character.isWhitespace(json.charAt(valueEnd))) {
            valueEnd++;
        }
        
        return json.substring(valueStart, valueEnd).trim();
    }
    
    // **CHANGE 7: IMPROVED OPTIONS PARSING**
    private void parseOptions(String json, Question q) {
        int optionsStart = json.indexOf("\"Options\"");
        if (optionsStart == -1) return;
        
        int objectStart = json.indexOf("{", optionsStart);
        if (objectStart == -1) return;
        
        // Find the complete Options object
        int braceCount = 1;
        int objectEnd = objectStart + 1;
        while (objectEnd < json.length() && braceCount > 0) {
            if (json.charAt(objectEnd) == '{') braceCount++;
            if (json.charAt(objectEnd) == '}') braceCount--;
            objectEnd++;
        }
        
        if (braceCount != 0) return;
        
        String optionsJson = json.substring(objectStart, objectEnd);
        
        // Extract each option
        q.setA(extractJsonValue(optionsJson, "A"));
        q.setB(extractJsonValue(optionsJson, "B"));
        q.setC(extractJsonValue(optionsJson, "C"));
        q.setD(extractJsonValue(optionsJson, "D"));
    }
}