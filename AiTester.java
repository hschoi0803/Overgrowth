import java.util.*;

public class AiTester
{
    Grid board;
    int player;
    
    public AiTester(Grid b, int p)
    {
        board = b;
        player = p;
    }
    
    public String makeMove()
    {
        ArrayList<Seed> moves = getValidMoves();
        if(moves.isEmpty())
            return "pass";
        Seed choice = moves.get((int)(Math.random()*moves.size()));
        String move = "";
        move += (char)(choice.col + (int)'A');
        move += (choice.row + 1);
        return move;
    }
    
    public ArrayList<Seed> getValidMoves()
    {
        ArrayList<Seed> list = new ArrayList<Seed>();
        for(Seed s : board.getBlankSeeds())
        {
            if(s.isValid(player) && !s.toString().equals(""))
            {
                list.add(s);
            }
        }
        return list;
    }
}