package com.jeopardy.writers;

import java.util.ArrayList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFWriter implements WriterStrategy {
    private String file;
    
    public PDFWriter(String file) {
        this.file = file;
    }
    
    @Override
    public void write(ArrayList<String> statements) {
        Document document = new Document();
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            
            for (String statement : statements) {
                document.add(new Paragraph(statement));
            }
            
            document.close();
            System.out.println("PDF report generated: " + file);
            
        } catch (FileNotFoundException e) {
            System.err.println("Error: Cannot create file " + file);
            e.printStackTrace();
        } catch (DocumentException e) {
            System.err.println("Error creating PDF document");
            e.printStackTrace();
        }
    }
}