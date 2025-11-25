package com.jeopardy.readers;

import java.util.ArrayList;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import com.jeopardy.Question;
import java.io.File;

public class XMLReader implements Reader {
    private String file;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;
    private ArrayList<Question> questions = new ArrayList<Question>();

    public XMLReader(String file) {
        this.file = file;
    }

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

    private String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
