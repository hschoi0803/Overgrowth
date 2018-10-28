public class UnrestrictedSeedGrid extends Grid
{
    public UnrestrictedSeedGrid()
    {
        for(int r = 0; r < grid.length; r++)
        {
            for(int c = 0; c < grid[r].length; c++)
            {
                grid[r][c] = new UnrestrictedSeed(0,this,r,c);
            }
        }
    }
}