import java.io.*;

public class MirrorGame extends AiVAi
{
    
    public void play()
    {
        Ai1 = new AiTester(game, 1);
        Ai2 = new AiTester(game, 2);
        String move = "";
        String mirrorMove = "";
        playerTurn = 1;
        numTurns = 0;
        hasPassed = false;
        growing = true;
        boolean invalidMove = false;
        while(growing)
        {
            if(numTurns == 0)
            {
                makeTurn("J10");
            }
            else if(playerTurn == 1)
            {
                if(move.equals("pass"))
                    makeTurn("pass");
                else
                {
                mirrorMove = "";
                mirrorMove += (char)((int)'A' + (83 - (int)(move.charAt(0)))) ;
                mirrorMove +=(20 - Integer.parseInt(move.substring(1)));
                }
                if(invalidMove)
                {
                    makeTurn("pass");
                }
                else
                    makeTurn(mirrorMove);
            }
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try{line = br.readLine();}catch(IOException e){System.out.println("IOEXception: " + e);}
            if(playerTurn == 2)
            {
                move = Ai2.makeMove();
                makeTurn(move);
                invalidMove = false;
            }
            
        }
    }
}