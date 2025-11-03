package com.jeopardy;

import java.util.Scanner;
import java.util.ArrayList;

public class Runner{
    public static void main(String[] args){
        ArrayList<Question> questions = new ArrayList<Question>();
        ArrayList<String> gameTurns = new ArrayList<String>();
        Player[] players = new Player[4];
        int i = 0;
        int playerNumber = 0;
        
        
        CSVReader csv = new CSVReader("C:\\Users\\Tristan\\Desktop\\Jeopardy\\game\\src\\main\\resources\\sample_game_CSV.csv");
        TXTWriter txt = new TXTWriter("C:\\Users\\Tristan\\Desktop\\Jeopardy\\game\\src\\main\\resources\\sample_game_report.txt");
        //DOCWriter doc = new DOCWriter("C:\\Users\\Tristan\\Desktop\\Jeopardy\\game\\src\\main\\resources\\sample_game_report.doc");
        questions = csv.read();
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of players:");
        int numberPlayers = scanner.nextInt();
        
        
        while (i < numberPlayers){
            Scanner scanName = new Scanner(System.in);
            System.out.println("Enter name of player:");
            String name = scanName.nextLine();
            Player newPlayer = new Player(name);
            players[i] = newPlayer;
            i++;
        }
        
        for (int numQuestions = 0; numQuestions < 2; numQuestions++){
            i = 0;
            if (playerNumber >= numberPlayers){
                playerNumber = 0;
            }
            Player currentPlayer = players[playerNumber];
            Scanner scanCategory = new Scanner(System.in);
            System.out.println(currentPlayer.getName() + ", enter category:");
            String category = scanCategory.nextLine();
            Scanner scanValue = new Scanner(System.in);
            System.out.println("Enter value:");
            int value = scanValue.nextInt();
            while (i < questions.size()){
                Question q = new Question();
                q = questions.get(i);
                if (q.getCategory().equals(category) && q.getValue() == value){
                    System.out.println(q.getStatement());
                    System.out.println("These are your choices:");
                    System.out.println(q.getA());
                    System.out.println(q.getB());
                    System.out.println(q.getC());
                    System.out.println(q.getD());
                    Scanner scanChoice = new Scanner(System.in);
                    System.out.println("Enter choice:");
                    String answer = scanChoice.nextLine();
                    if (answer.equals(q.getCorrect())){
                        System.out.println("Correct");
                        currentPlayer.updatePoints(value);
                    }
                    else{
                        System.out.println("Wrong");
                    }
                    gameTurns.add("Turn " + numQuestions + currentPlayer.getName() + " selected " + q.getCategory() + " for " + q.getValue());
                    gameTurns.add("\nQuestion " + q.getStatement());
                    gameTurns.add("\nAnswer: " + answer + " - (" + q.getValue() + "pts)");
                    gameTurns.add("\nScore after turn: " + currentPlayer.getName() + " " + currentPlayer.getPoints() + "\n"); 
                }
                i++;
            }
            playerNumber++;
        }
        txt.write(gameTurns);
    }
}