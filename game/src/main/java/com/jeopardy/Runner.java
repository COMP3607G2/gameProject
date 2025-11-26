package com.jeopardy;

import java.util.Scanner;
import com.jeopardy.readers.Reader;
import com.jeopardy.readers.CSVReader;
import com.jeopardy.readers.JSONReader;
import com.jeopardy.readers.XMLReader;
import com.jeopardy.writers.CSVLogger;
import com.jeopardy.writers.DOCWriter;
import com.jeopardy.writers.PDFWriter;
import com.jeopardy.writers.TXTWriter;
import com.jeopardy.writers.WriterStrategy;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Encapsulates game logic
 * 
 */
public class Runner{
    /**
     * Facilitates running the game
     * @param args command line
     */
    public static void main(String[] args){
        ArrayList<Question> questions = new ArrayList<Question>();
        ArrayList<ScoreDisplay> displays = new ArrayList<ScoreDisplay>();
        ArrayList<String> gameTurns = new ArrayList<String>();
        HashMap<String, Category> categories = new HashMap<>();
        WriterContext wtx = new WriterContext();
        Player[] players = new Player[4];
        Reader rd;
        WriterStrategy output;
        boolean repeat = false;
        int count = 0, playerNumber = 0;
        
        CSVLogger log = new CSVLogger("sample_game_event_log.csv");
        log.logger("System", "Start Game", "", "", "", "", "");
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file input format(CSV/JSON/XML): ");
        String type = scanner.nextLine();
        type = type.toUpperCase();

        if (type.equals("CSV")){
            String path = Runner.class.getResource("/sample_game_CSV.csv").getPath();
            path = URLDecoder.decode(path, StandardCharsets.UTF_8);
            rd = new CSVReader(path);
        }
        else if (type.equals("JSON")){
            String path = Runner.class.getResource("/sample_game_JSON.json").getPath();
            path = URLDecoder.decode(path, StandardCharsets.UTF_8);
            rd = new JSONReader(path);
        }
        else{
            String path = Runner.class.getResource("/sample_game_XML.xml").getPath();
            path = URLDecoder.decode(path, StandardCharsets.UTF_8);
            rd = new XMLReader(path);
        }
        
        questions = rd.read();
        for (Question q : questions){
            String cat = q.getCategory();
            Category category = categories.get(cat);
            if (category == null){
                category = new Category(cat);
                categories.put(cat, category);
            }
            category.add(q);
        }

        log.logger("System", "Load File", "", "", "", "Success", "");
        gameTurns.add("JEOPARDY PROGRAMMING GAME REPORT\n================================\n\nPlayers: ");

        System.out.println("\nEnter number of players:");
        int numberPlayers = scanner.nextInt();
        scanner.nextLine();
        log.logger("System", "Select Player Count", "", "", Integer.toString(numberPlayers), "N/A", "");

        while (count < numberPlayers){
            int number = count + 1;
            System.out.println("\nEnter name of player " + number + ":");
            String name = scanner.nextLine();
            Player newPlayer = new Player(name);
            players[count] = newPlayer;
            gameTurns.add(name);
            if (count < numberPlayers - 1){
                gameTurns.add(", ");
            }
            displays.add(new ScoreDisplay(name));
            count++;
            log.logger(name, "Enter Player Name", "", "", name, "N/A", "");
        }
       
        gameTurns.add("\n\nGameplay Summary:\n-----------------\n");

        for (int j = 0; j < numberPlayers; j++){
            for (ScoreDisplay display : displays){
                players[j].addObserver(display);
            }
        }

        int numQuestions = 0;
        while (numQuestions < questions.size()){
            if (playerNumber >= numberPlayers){
                playerNumber = 0;
            }
            Player currentPlayer = players[playerNumber];
            System.out.println("\n" + currentPlayer.getName() + ", enter category:");
            String cat = scanner.nextLine();
            log.logger(currentPlayer.getName(), "Select Category", cat, "", "", "", "");
            System.out.println("Enter value:");
            int value = scanner.nextInt();
            scanner.nextLine();
            log.logger(currentPlayer.getName(), "Select Question", cat, Integer.toString(value), "", "", "");
            Category category = categories.get(cat);
            for (Question q : category.getList()){
                if (q.getValue() == value){
                    if (q.ifAnswered()){
                        repeat = true;
                        System.out.println("Question chosen already");
                    }
                    else{
                        q.answered();
                        System.out.println("\n" + q.getStatement());
                        System.out.println("These are your choices:");
                        System.out.println("A) " + q.getA() + "\nB) " + q.getB()+ "\nC) " + q.getC()+ "\nD) " + q.getD());
                        System.out.println("Enter choice:");
                        String answer = scanner.nextLine();
                        answer = answer.toUpperCase();
                        String wc;
                        if (answer.equals(q.getCorrect())){
                            wc = "Correct";
                            currentPlayer.updatePoints(value);
                        }
                        else{
                            wc = "Wrong";
                            currentPlayer.updatePoints(-value);
                        }
                        log.logger(currentPlayer.getName(), "Answer Question", cat, Integer.toString(value), q.getAnswer(answer), wc, Integer.toString(currentPlayer.getPoints()));
                        System.out.println("\nThe answer was " + wc + ".\n");
                        gameTurns.add("Turn " + (numQuestions + 1) + ": " + currentPlayer.getName() + " selected " + q.getCategory() + " for " + q.getValue() + " pts");
                        gameTurns.add("\nQuestion: " + q.getStatement());
                        gameTurns.add("\nAnswer: " + q.getAnswer(answer) + " - " + wc + "(" + q.getValue() + "pts)");
                        gameTurns.add("\nScore after turn: " + currentPlayer.getName() + " = " + currentPlayer.getPoints() + "\n\n");
                    } 
                }
            }
            if (!repeat){
                numQuestions++;
                playerNumber++;
                System.out.println("Categories: ");
                for (Category c : categories.values()){
                    String ans = "";
                    if (c.ifAnswered()){
                        ans = "All questions answered.";
                    }
                    else{
                        ans = "All questions not answered.";
                    }
                    System.out.println("Category- " + c.getName() + ": " + ans);
                }
                System.out.println("\nDo you want to continue?(Y/N): ");
                String c = scanner.nextLine();
                c = c.toUpperCase();
                if (c.equals("N")){
                    break;
                }
            }
            repeat = false;
        }

        gameTurns.add("Final Scores:");
        count  = 0;
        while (count < numberPlayers){
            gameTurns.add("\n" + players[count].getName() + ": " + players[count].getPoints());
            count++;
        }

        gameTurns.add("\n");
        System.out.println("Enter report format(TXT/DOC/PDF): ");
        String report = scanner.nextLine();
        report = report.toUpperCase();
        if (report.equals("TXT")){
            output = new TXTWriter("sample_game_report.txt");
        }
        else if (report.equals("DOC")){
            output = new DOCWriter("sample_game_report.doc");
        }
        else{
            output = new PDFWriter("sample_game_report.pdf");
        }
        wtx.setWriterStrategy(output);
        wtx.list(gameTurns);

        log.logger("System", "Generate Report", "", "", "", "N/A", "");
        log.logger("System", "Generate Event Log", "", "", "", "N/A", "");
        log.logger("System", "Exit Game", "", "", "", "", "");

        scanner.close();
    }   
}