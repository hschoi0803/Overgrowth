public class MoveTester
{
    SmartAi tester;
    double maxPoints;
    double minPoints;
    public void test()
    {
        String move = "";
        double[][] points = new double[19][19];
        tester = new SmartAi(new Grid(), 1);
        for(Seed s : tester.getValidMoves())
        {
            double temp = tester.testMove(s);
            points[s.row][s.col] = temp;;
            //System.out.println(s + " calculated");
        }
        for(int r = 0; r<points.length;r++)
        {
            System.out.print(r+1 + "|");
            for(int c= 0; c<points[r].length; c++)
            {
                System.out.print(points[r][c] + "|");
            }
            System.out.println();
        }
    }
}