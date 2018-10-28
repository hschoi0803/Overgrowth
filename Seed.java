import java.util.*;

public class Seed
{
    public int seedType;
    public Grid grid;
    int row;
    int col;
    
    public Seed(int type, Grid gr, int r, int c)
    {
        seedType = type;
        grid = gr;
        row = r;
        col = c;
    }
    
    public Grid getGrid(){return grid;}
    
    public int getSeedType(){return seedType;}
    
    public void setSeedType(int type){seedType = type;}
    
    public boolean isEmpty(){
        if(seedType == 0)
            return true;
        return false;
    }
    
    public ArrayList<Seed> getAdjacentSeeds()
    {
        ArrayList<Seed> list = new ArrayList<Seed>();
        for(int r = this.row - 1; r <= this.row +1; r++)
        {
            for(int c = this.col - 1; c <= this.col +1; c++)
            {

                if(grid.withinBounds(r,c) && this.grid.get(r,c) != this /*&& this.grid.get(r,c).getSeedType() != 0*/){
                    list.add(grid.get(r,c));
                }
            }
        }
        
        
    
        /*
        list.add(grid[this.row+1][this.col]);
        list.add(grid[this.row+1][this.col-1]);
        list.add(grid[this.row+1][this.col+1]);
        list.add(grid[this.row-1][this.col]);
        list.add(grid[this.row-1][this.col-1]);
        list.add(grid[this.row-1][this.col+1]);
        list.add(grid[this.row][this.col+1]);
        list.add(grid[this.row][this.col-1]);
        */
        return list;
    }
    
    public ArrayList<Seed> getSupportingSeeds()
    {
        ArrayList<Seed> list = new ArrayList<Seed>();
        for(Seed s : getAdjacentSeeds())
        {
            if(s.getSeedType() == (this.getSeedType()))
                list.add(s);
        }
        return list;
    }
    
    public ArrayList<Seed> getAttackingSeeds()
    {
        ArrayList<Seed> list = new ArrayList<Seed>();
        for(Seed s : getAdjacentSeeds())
        {
            if(!(s.getSeedType() == (this.getSeedType())) && s.getSeedType() != 0)
                list.add(s);
        }
        return list;
    }
    
    public boolean spreadCondition()
    {
        if(getAttackingSeeds().size() > 0) //StandOff condition
            return false;
        
        if(getSupportingSeeds().size() >= 2) //Stagnant condition
            return false;
        
        return true;
    }
    
    public boolean deathCondition()
    {
        if(getAttackingSeeds().size() > getSupportingSeeds().size())
            return true;
        return false;
    }
    
    public boolean isValid(int seedType)
    {
        if(this.getSeedType() != 0)
            return false;
        Seed temp = new Seed(seedType, this.grid, this.row, this.col);
        if(temp.getAttackingSeeds().size() > temp.getSupportingSeeds().size())
            return false;
        return true;
    }
    
    public boolean effectivelySeeded(int seedType)
    {
        Seed temp = new Seed(seedType, this.grid, this.row, this.col);
        if(temp.getSupportingSeeds().size() > this.getAdjacentSeeds().size()/2)
            return true;
        return false;
    }
    
    public void spread()
    {
        if(grid.withinBounds(this.row + 1, this.col))
            this.grid.set(this.seedType, this.row + 1, this.col);
        if(grid.withinBounds(this.row - 1, this.col))
            this.grid.set(this.seedType, this.row - 1, this.col);
        if(grid.withinBounds(this.row , this.col + 1))
            this.grid.set(this.seedType, this.row, this.col + 1);
        if(grid.withinBounds(this.row, this.col - 1))
            this.grid.set(this.seedType, this.row, this.col - 1);
    }
    
    public void die()
    {
        if(this.getSeedType() == 1)
            this.seedType = 2;
        else
            this.seedType = 1;
    }
    
    public void act()
    {
        if(seedType == 0)
            return;
        if(spreadCondition())
        {
            this.spread();
            for(Seed s : getAdjacentSeeds())
                s.act();
        }
        if(deathCondition())
        {
            this.die();
            for(Seed s : getAdjacentSeeds())
                s.act();
        }
    }
    
    public String toString()
    {
        return "" + (char)(this.col + (int)'A') + (this.row + 1);
    }
}