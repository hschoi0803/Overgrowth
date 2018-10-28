import java.util.*;

public class SmartAi extends AiTester
{
    double maxPoints;
    double minPoints;
    ArrayList<Seed> initialMoves = new ArrayList<Seed>();
    
    public SmartAi(Grid b, int p)
    {
        super(b,p);
        maxPoints = 0;
        minPoints = Integer.MAX_VALUE;
        initialMoves.add(board.get("C",16));
        initialMoves.add(board.get("C",2));
        initialMoves.add(board.get("Q",16));
        initialMoves.add(board.get("Q",2));
    }
    
    public String makeMove()
    {
        String move = "";
        for(Seed s : initialMoves)
        {
            if(getValidMoves().contains(s))
            {
                return s.toString();
            }
        }
        if(getValidMoves().isEmpty())
            return "pass";
        maxPoints = 0;
        minPoints = 0;
        for(Seed s : getValidMoves())
        {
            double temp = testMove(s);
            if(temp > maxPoints)
            {
                maxPoints = temp;
                move = s.toString();
            }
            if(temp < minPoints)
            {
                minPoints = temp;
            }
            //System.out.println(s + " calculated");
        }
        //System.out.println(move);
        if(minPoints == maxPoints)
            return "pass";
        return move;
    }
    
    public double testMove(Seed s)
    {
    
        int points = 0;
        
        //point advantage from move
        Grid simulatedGrid = new Grid(board);
        Grid originalGrid = new Grid(board);
        simulatedGrid.place(player, s.row, s.col);
        points +=  simulatedGrid.calculatePoints()[player - 1] - originalGrid.calculatePoints()[player - 1];
        
        //prevent conversion into opponent's seed
        simulatedGrid = new Grid(board);
        originalGrid = new Grid(board);
        simulatedGrid.place(player%2 + 1, s.row, s.col);
        points += (5*(originalGrid.getSeeds(player).size() - simulatedGrid.getSeeds(player).size()));
        
        //consider conversion of opponent's seeds
        simulatedGrid = new Grid(board);
        originalGrid = new Grid(board);
        simulatedGrid.place(player, s.row, s.col);
        points += (5*(originalGrid.getSeeds(player%2 + 1).size() - simulatedGrid.getSeeds(player%2 + 1).size()));
        /*
        int points = 0;
        for(int c = 0; c<100; c++)
        {
            AiVAi sim = new AiVAi(new Grid(board), this.player, this.board.hasPassed);
            sim.game.place(this.player,s.row,s.col);
            sim.play();
            points += sim.game.calculatePoints()[this.player-1];
        }
        return points/100;
        */
        return points;
    }
}