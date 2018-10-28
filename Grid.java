import java.util.*;

public class Grid
{
    public Seed[][] grid = new Seed[19][19];
    int turn;
    boolean hasPassed;
    
    public Grid()
    {
        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[r].length; c++)
            {
                grid[r][c] = new Seed(0,this,r,c);
            }
        }
        turn = 0;
        hasPassed = false;
    }
    
    public Grid(Grid g)
    {
        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[r].length; c++)
            {
                grid[r][c] = new Seed(g.get(r,c).getSeedType(), this, r, c);
            }
        }
        turn = g.turn;
        this.hasPassed = g.hasPassed;
    }
    
    public Seed get(int row, int col)
    {
        return grid[row][col];
    }
    
    public Seed get(String col, int row)
    {
        return get(row, (int)col.charAt(0) - (int)'A');
    }
    
    public void set(int seedType, int row, int col)
    {
        grid[row][col].seedType = seedType;
    }
    
    public ArrayList<Seed> getBlankSeeds()
    {
        ArrayList<Seed> list = new ArrayList<Seed>();
        for(int r = 0; r < grid.length; r ++)
        {
            for(int c = 0; c < grid[r].length; c++)
            {
                if(grid[r][c].getSeedType() == 0 && grid[r][c] != null)
                    list.add(grid[r][c]);
            }
        }
        return list;
    }
    
    public ArrayList<Seed> getSeeds(int type)
    {
        ArrayList<Seed> list = new ArrayList<Seed>();
        for(int r = 0; r < grid.length; r ++)
        {
            for(int c = 0; c < grid[r].length; c++)
            {
                if(grid[r][c].getSeedType() == type)
                    list.add(grid[r][c]);
            }
        }
        return list;
    }
    
    public void gridAct()
    {
        for(int r = 0; r < grid.length; r ++)
        {
            for(int c = 0; c < grid[r].length; c++)
            {
                grid[r][c].act();
            }
        }
    }
    
    public int[] calculatePoints()
    {
        int[] scores = new int[2];
        int p1points = 0;
        int p2points = 0;
        Grid temp = new Grid(this);
        boolean allChecked = false;
        
        while(!allChecked)
        {
            allChecked = true;
            for(Seed s : temp.getBlankSeeds())
            {
                if(s.effectivelySeeded(1)){
                    s.setSeedType(1);
                    allChecked = false;
                }
                if(s.effectivelySeeded(2)){
                    s.setSeedType(2);
                    allChecked = false;
                }
            }
        }
        
        temp.gridAct();
        p1points = temp.getSeeds(1).size();
        p2points = temp.getSeeds(2).size();
        
        scores[0] = p1points;
        scores[1] = p2points;
        
        return scores;
    }
    
    public boolean place(int seedType, int row, int col)
    {
        if(!withinBounds(row, col) || !grid[row][col].isValid(seedType) )
            return false;
        
        grid[row][col].seedType = seedType;
        grid[row][col].act();
        for(Seed s : grid[row][col].getAdjacentSeeds())
        {
            s.act();
        }
        turn++;
        return true;
    }
    
    public boolean withinBounds(int row, int col)
    {
        if(!(row >= 0 && col >= 0))
            return false;
        if(row >= grid.length || col >= grid[row].length)
            return false;
        return true;
    }
    
    public String toString()
    {
        String output = "   | A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S |\n";
        String boundary = "   ------------------------------------------------------------------------------\n";
        output += boundary;
        for(int r = 0; r < grid.length; r++)
        {
            int colLength = 0;
            output += (r+1);
            if(r < 9)
                output += " ";
            output += " |";
            for(int c = 0; c< grid[r].length; c++)
            {
                if(grid[r][c].getSeedType() == 0)
                    output += "   |";
                if(grid[r][c].getSeedType() == 1)
                    output +=  " O |";
                if(grid[r][c].getSeedType() == 2)
                    output += " X |";
                
                if(c == grid.length - 1)
                    output += "\n";
                colLength = c;
            }
            output += boundary;
        }
        return output;
    }
    
    
    
    /*
    public ArrayList<Seed> getAdjacentSeeds(Seed s)
    {
        ArrayList<Seed> list = new ArrayList<Seed>();
        list.add(grid[s.row+1][s.col]);
        list.add(grid[s.row+1][s.col-1]);
        list.add(grid[s.row+1][s.col+1]);
        list.add(grid[s.row-1][s.col]);
        list.add(grid[s.row-1][s.col-1]);
        list.add(grid[s.row-1][s.col+1]);
        list.add(grid[s.row][s.col+1]);
        list.add(grid[s.row][s.col-1]);
        return list;
    }
    */
    
}