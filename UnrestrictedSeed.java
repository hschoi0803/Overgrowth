public class UnrestrictedSeed extends Seed
{
    public UnrestrictedSeed(int type, Grid gr, int r, int c)
    {
        super(type, gr, r, c);
    }
    
    public boolean spreadCondition()
    {
        if(getSupportingSeeds().size() >= 2) //Stagnant condition
            return false;
        
        return true;
    }
    
    public void spread()
    {
        if(grid.withinBounds(this.row + 1, this.col) && grid.get(this.row + 1, this.col).getSeedType() == 0)
            this.grid.set(this.seedType, this.row + 1, this.col);
        if(grid.withinBounds(this.row - 1, this.col) && grid.get(this.row - 1, this.col).getSeedType() == 0)
            this.grid.set(this.seedType, this.row - 1, this.col);
        if(grid.withinBounds(this.row , this.col + 1) && grid.get(this.row, this.col + 1).getSeedType() == 0)
            this.grid.set(this.seedType, this.row, this.col + 1);
        if(grid.withinBounds(this.row, this.col - 1) && grid.get(this.row, this.col - 1).getSeedType() == 0)
            this.grid.set(this.seedType, this.row, this.col - 1);
    }
}