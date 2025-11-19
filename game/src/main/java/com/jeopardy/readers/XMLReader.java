package com.jeopardy.readers;

import java.util.ArrayList;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
        factory = DocumentBuilderFactory.newInstance();
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                document = builder.parse(file);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        NodeList nodes = document.getElementsByTagName("QuestionItem");
           for (int i = 0; i < nodes.getLength(); i++){
               Element e = (Element) nodes.item(i);
               Question q = new Question();
               q.setCategory(e.getElementsByTagName("Category").item(0).getTextContent());
               //q.setValue(Integer.parseInt(e.getElementsByTagName("Value").item(0).getTextContent()));
               q.setStatement(e.getElementsByTagName("Value").item(0).getTextContent());
               //Element options = (Element) e.getElementsByTagName("Options").item(0);
               q.setA(e.getElementsByTagName("OptionA").item(0).getTextContent());
               q.setB(e.getElementsByTagName("OptionB").item(0).getTextContent());
               q.setC(e.getElementsByTagName("OptionC").item(0).getTextContent());
               q.setD(e.getElementsByTagName("OptionD").item(0).getTextContent());
               q.setCorrect(e.getElementsByTagName("CorrectAnswer").item(0).getTextContent());
               questions.add(q);
            }     
       return questions;
    }
}

