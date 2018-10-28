import java.io.*;
public class AiVAi extends PublicTester
{
    AiTester Ai1;
    AiTester Ai2;
    double p1score = 0;
    double p2score = 0;
    int totalGames;
    int totalNumTurns;
    
    public AiVAi()
    {
        super();
        Ai1 = new AiTester(game,1);
        Ai2 = new AiTester(game,2);
    }
    
    public AiVAi(Grid g, int turn, boolean passed)
    {
        super();
        game = g;
        playerTurn = turn;
        hasPassed = passed;
        Ai1 = new AiTester(game,1);
        Ai2 = new AiTester(game,2);
    }
    
    public void play()
    {
        
        while(growing)
        {
            if(playerTurn == 1)
            {
                this.makeTurn(Ai1.makeMove());
            }
            if(playerTurn == 2)
            {
                this.makeTurn(Ai2.makeMove());
            }
            /*
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try{line = br.readLine();}catch(IOException e){System.out.println("IOEXception: " + e);}
            */
        }
        
            
    }
    
    public void endGame()
    {
        growing = false;
        int[] score = game.calculatePoints();
        p1score += score[0];
        p2score += score[1];
        totalNumTurns += numTurns;
        numTurns = 0;
        totalGames++;
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
            //System.out.println("Player " + playerTurn + " has passed.");
            numTurns ++;
            if(hasPassed)
            {
                endGame();
                return;
            }
            else
            {
                hasPassed = true;
                game.hasPassed = true;
            }
            if(playerTurn == 1)
                playerTurn = 2;
            else
                playerTurn = 1;
            //System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
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
            //System.out.println(game);
            //System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
            return;
        }
        
        if(line.equals("reset"))
        {
            game = new Grid();
            oldBoard = new Grid();
            playerTurn = 1;
            numTurns = 1;
            hasPassed = false;
            game.hasPassed = false;
            growing = true;
            //System.out.println("Starting OverGrowth!");
            //System.out.println(game);
            //System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
            return;
        }
        
        
        if(line.length() >3 || line.length() < 2)
        {
            //System.out.println("Invalid command, try again: ");
            return; 
        }
        
        Grid temp = new Grid(game);
        
        line = line.toUpperCase();
        
        try{
            if(!game.place(playerTurn, Integer.parseInt(line.substring(1)) - 1, ((int)line.charAt(0) - (int)'A')))
            {
                //System.out.println("Invalid move!");
                //System.out.println("Try again: ");
                //System.out.println(game);
                return;
            }
            else
            {
                oldBoard = temp;
                numTurns ++;
                hasPassed = false;
                game.hasPassed = false;
                //System.out.println(game);
                if(playerTurn == 1)
                    playerTurn = 2;
                else
                    playerTurn = 1;
                //System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
                undone = false;
                return;
            }
        }catch(NumberFormatException e){
             //System.out.println("Invalid command, try again: ");
            return; 
        }
    }
    
    
    public void test()
    {
        for(int c = 0; c < 10; c++)
        {
            play();
            game = new Grid();
        }
//         System.out.println("player 1 total score: " + p1score);
//         System.out.println("player 2 total score: " + p2score);
//         System.out.println("player 1 average score: " + p1score/totalGames);
//         System.out.println("player 2 average score: " + p2score/totalGames);
    }
}