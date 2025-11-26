
package com.jeopardy.readers;

import java.util.ArrayList;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import com.jeopardy.Question;
import java.io.File;

/**
 * Reads XML files for Questions
 */
public class XMLReader implements Reader {
    /**The file path that is read */
    private String file;
    /**Factory for DocumentBuilders */
    private DocumentBuilderFactory factory;
    /**Parser */
    private DocumentBuilder builder;
    /**Parsed document */
    private Document document;
    /**The questions that would be used in the game */
    private ArrayList<Question> questions = new ArrayList<Question>();

    /**
    * Creates a XMLReader
    * @param file file to be read from
    */
    public XMLReader(String file) {
        this.file = file;
    }

    /**
    * Reads the questions from CSV file
    * @return all questions
    */
    @Override
    public ArrayList<Question> read() {
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(new File(file));
            document.getDocumentElement().normalize();

            NodeList nodes = document.getElementsByTagName("QuestionItem");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element e = (Element) nodes.item(i);
                Question q = new Question();

                q.setCategory(getElementText(e, "Category"));

                String valueText = getElementText(e, "Value");
                try {
                    q.setValue(Integer.parseInt(valueText));
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid value format for question " + i + ": " + valueText);
                    q.setValue(0);
                }

                q.setStatement(getElementText(e, "QuestionText"));

                // Get options
                NodeList optionsList = e.getElementsByTagName("Options");
                if (optionsList.getLength() > 0) {
                    Element optionsElement = (Element) optionsList.item(0);
                    q.setA(getElementText(optionsElement, "OptionA"));
                    q.setB(getElementText(optionsElement, "OptionB"));
                    q.setC(getElementText(optionsElement, "OptionC"));
                    q.setD(getElementText(optionsElement, "OptionD"));
                }

                q.setCorrect(getElementText(e, "CorrectAnswer"));

                questions.add(q);
            }

        } catch (Exception e) {
            System.err.println("Error reading XML file: " + e.getMessage());
            e.printStackTrace();
        }

        return questions;
    }

    /**
    * Gets content from parent element
    * @param parent parent XML
    * @param tagName tag name
    * @return content
    */
    private String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}