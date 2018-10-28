import java.util.*;
import java.io.*;

public class PublicTester
{
    Grid game;
    Grid oldBoard;
    int playerTurn;
    int numTurns;
    String move;
    boolean hasPassed;
    boolean growing; 
    boolean undone;
    
    public PublicTester()
    {
        game = new Grid();
        oldBoard = new Grid();
        playerTurn = 1;
        numTurns = 0;
        hasPassed = false;
        growing = true;
        undone = false;
    }
    
    
    public void play()
    {
        
        System.out.println("Starting OverGrowth!");
        System.out.println(game);
        System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
        while(growing)
        {
            move = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try{move = br.readLine();}catch(IOException e){System.out.println("IOEXception: " + e);}
            
            makeTurn(move);
        }
    }
    
    public void makeTurn(String line)
    {
        
        if(line.equals("resign"))
        {
            if(playerTurn == 1)
                endGame(2);
            if(playerTurn == 2)
                endGame(1);
            return;
        }
        
        if(line.equals("pass"))
        {
            System.out.println("Player " + playerTurn + " has passed.");
            numTurns ++;
            if(hasPassed)
            {
                endGame();
                return;
            }
            else
            {
                hasPassed = true;
            }
            if(playerTurn == 1)
                playerTurn = 2;
            else
                playerTurn = 1;
            System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
            undone = false;
            return;
        }
        
        if(line.equals("undo"))
        {
            Grid temp = game;
            game = oldBoard;
            oldBoard = temp;
            if(playerTurn == 1)
                playerTurn = 2;
            else
                playerTurn = 1;
            if(undone == false)
                numTurns--;
            if(undone == true)
                numTurns++;
            undone = !undone;
            System.out.println(game);
            System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
            return;
        }
        
        if(line.equals("reset"))
        {
            game = new Grid();
            oldBoard = new Grid();
            playerTurn = 1;
            numTurns = 0;
            hasPassed = false;
            growing = true;
            System.out.println("Starting OverGrowth!");
            System.out.println(game);
            System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
            return;
        }
        
        
        if(line.length() >3 || line.length() < 2)
        {
            System.out.println("Invalid command, try again: ");
            return; 
        }
        
        Grid temp = new Grid(game);
        
        line = line.toUpperCase();
        
        try{
            if(!game.place(playerTurn, Integer.parseInt(line.substring(1)) - 1, ((int)line.charAt(0) - (int)'A')))
            {
                System.out.println("Invalid move!");
                System.out.println("Try again: ");
                System.out.println(game);
                return;
            }
            else
            {
                oldBoard = temp;
                numTurns ++;
                hasPassed = false;
                System.out.println(game);
                if(playerTurn == 1)
                    playerTurn = 2;
                else
                    playerTurn = 1;
                System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
                undone = false;
                return;
            }
        }catch(NumberFormatException e){
             System.out.println("Invalid command, try again: ");
            return; 
        }
    }
    
    public void endGame()
    {
        growing = false;
        System.out.println("OverGrowth has ended: ");
        System.out.println(game);
        int[] score = game.calculatePoints();
        if(score[0] > score[1])
            System.out.println("The winner is Player 1!");
        else
            System.out.println("The winner is Player 2!");
        System.out.println("Number of Turns: " + numTurns);
        System.out.println("Final score: ");
        System.out.println("Player 1: " + score[0]);
        System.out.println("Player 2: " + score[1]);
    }
    
    public void endGame(int winner)
    {
        growing = false;
        System.out.println("OverGrowth has ended: ");
        System.out.println(game);
        int[] score = game.calculatePoints();
        System.out.println("The winner is Player " + winner + "!");
        System.out.println("Number of Turns: " + numTurns);
        System.out.println("Final score: ");
        System.out.println("Player 1: " + score[0]);
        System.out.println("Player 2: " + score[1]);
    }
}