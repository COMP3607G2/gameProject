package com.jeopardy;

import java.util.Scanner;

import com.jeopardy.readers.CSVReader;
import com.jeopardy.writers.CSVLogger;
import com.jeopardy.writers.TXTWriter;

import java.util.ArrayList;

public class Runner{
    public static void main(String[] args){
        ArrayList<Question> questions = new ArrayList<Question>();
        //ArrayList<Question> chosen = new ArrayList<Question>();
        ArrayList<ScoreDisplay> displays = new ArrayList<ScoreDisplay>();
        ArrayList<String> gameTurns = new ArrayList<String>();

        Player[] players = new Player[4];
        int i = 0;
        int playerNumber = 0;
        String wc;

        CSVLogger log = new CSVLogger("C:\\Users\\Tristan\\Desktop\\Jeopardy\\game\\src\\main\\resources\\sample_game_event_log.csv");
        log.logger("System", "Start Game", "", "", "", "", "");
        
        
        Scanner scanner = new Scanner(System.in);
        CSVReader rd = new CSVReader("C:\\Users\\Tristan\\Desktop\\Jeopardy\\game\\src\\main\\resources\\sample_game_CSV.csv");
        
        TXTWriter txt = new TXTWriter("sample_game_report.txt");
        //DOCWriter doc = new DOCWriter("C:\\Users\\Tristan\\Desktop\\Jeopardy\\game\\src\\main\\resources\\sample_game_report.doc");
        WriterContext wtx = new WriterContext();
        wtx.setWriterStrategy(txt);
        questions = rd.read();
        log.logger("System", "Load File", "", "", "", "Success", "");
        
        gameTurns.add("JEOPARDY PROGRAMMING GAME REPORT\n================================\n\n");
        gameTurns.add("Players: ");

        System.out.println("Enter number of players:");
        int numberPlayers = scanner.nextInt();
        scanner.nextLine();
        log.logger("System", "Select Player Count", "", "", Integer.toString(numberPlayers), "N/A", "");
        
        while (i < numberPlayers){
            System.out.println("Enter name of player:");
            String name = scanner.nextLine();
            Player newPlayer = new Player(name);
            players[i] = newPlayer;
            i++;
            gameTurns.add(name + ",");
            displays.add(new ScoreDisplay(name));
            log.logger(name, "Enter Player Name", "", "", name, "N/A", "");
        }

        for (int j = 0; j < numberPlayers; j++){
            for (ScoreDisplay display : displays){
                players[j].addObserver(display);
            }
        }

        gameTurns.add("\n\nGameplay Summary:\n-----------------\n");
        
        for (int numQuestions = 0; numQuestions < 2; numQuestions++){
            i = 0;
            if (playerNumber >= numberPlayers){
                playerNumber = 0;
            }
            Player currentPlayer = players[playerNumber];
            System.out.println("\n" + currentPlayer.getName() + ", enter category:");
            String category = scanner.nextLine();
            log.logger(currentPlayer.getName(), "Select Category", category, "", "", "", "");
            System.out.println("Enter value:");
            int value = scanner.nextInt();
            scanner.nextLine();
            log.logger(currentPlayer.getName(), "Select Question", category, Integer.toString(value), "", "", "");
            while (i < questions.size()){
                //boolean exists = false;
                Question q = new Question();
                q = questions.get(i);
                if (q.getCategory().equals(category) && q.getValue() == value){
                    //for (Question question : chosen){
                    //    if (question == q){
                    //        exists = true;
                    //    }
                    //}
                    //if (exists){
                    //    System.out.println("Question chosen already");
                    
                    //else{
                    //    chosen.add(q);
                        System.out.println(q.getStatement());
                        System.out.println("These are your choices:");
                        System.out.println("A) " + q.getA() + "\nB) " + q.getB()+ "\nC) " + q.getC()+ "\nD) " + q.getD());
                        System.out.println("Enter choice:");
                        String answer = scanner.nextLine();
                        if (answer.equals(q.getCorrect())){
                            wc = "Correct";
                            currentPlayer.updatePoints(value);
                        }
                        else{
                            wc = "Wrong";
                            currentPlayer.updatePoints(-value);
                        }
                        log.logger(currentPlayer.getName(), "Answer Question", category, Integer.toString(value), answer, wc, Integer.toString(currentPlayer.getPoints()));
                        System.out.println(wc);
                        gameTurns.add("Turn " + (numQuestions + 1) + ": " + currentPlayer.getName() + " selected " + q.getCategory() + " for " + q.getValue() + " points.");
                        gameTurns.add("\nQuestion: " + q.getStatement());
                        gameTurns.add("\nAnswer: " + q.getCorrect() + " - " + wc + "(" + q.getValue() + "pts)");
                        gameTurns.add("\nScore after turn: " + currentPlayer.getName() + " = " + currentPlayer.getPoints() + "\n\n");
                    //} 
                }
                i++;
            }
            playerNumber++;
        
        
        }
        gameTurns.add("Final Scores:");
        int count  = 0;
        while (count < numberPlayers){
            gameTurns.add("\n" + players[count].getName() + ":" + players[count].getPoints());
            count++;
        }
        gameTurns.add("\n");
        wtx.list(gameTurns);
        log.logger("System", "Generate Report", "", "", "", "N/A", "");
        log.logger("System", "Generate Event Log", "", "", "", "N/A", "");
        log.logger("System", "Exit Game", "", "", "", "", "");

        scanner.close();
    }   
}
