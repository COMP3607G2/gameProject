package com.jeopardy.readers;

import java.util.ArrayList;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jeopardy.Question;

import java.beans.Statement;
import java.io.IOException;

public class XMLReader implements Reader{
    private String file;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;
    private ArrayList<Question> questions = new ArrayList<Question>();

    public XMLReader(String file){
       this.file = file;  
    }
   
   public ArrayList<Question> read(){
            try {
                factory = DocumentBuilderFactory.newInstance();
                builder = factory.newDocumentBuilder();
                document = builder.parse(new File(file));
                document.getDocumentElement().normalize();

                NodeList node = document.getElementsByTagName("QuestionItem");

                for (int i = 0; i < nodes.getLength(); i++ ){
                    Element e = (Element) nodes.item(i);
                    Question q = new Question();

                    q.setCategory(getElementsByTagName(e, "Category"));

                    String valueText = getElementsByTagName(e, "Value");

                    try {
                        q.setValue(Integer.parseInt(valueText));
                    } catch (NumberFormatException ex) {
                        System.err.println("Invalid value format for question " + i + ": " + valueText);
                        q.setValue(0);
                    }

                    q.setStatement(getElementsByTagName(e, Statement));

                    q.setA(getElementsByTagName(e, "OptionA"));
                    q.setB(getElementsByTagName(e, "OptionB"));
                    q.setC(getElementsByTagName(e, "OptionC"));
                    q.setD(getElementsByTagName(e, "OptionD"));

                    q.setCorrect(getElementsByTagName(e, "CorrectAnswer"));

                    questions.add(q);

                }

            } catch (Exception e) {
            System.err.println("Error reading XML file: " + e.getMessage());
            e.printStackTrace();
        }
        
        return questions;
    }
}

