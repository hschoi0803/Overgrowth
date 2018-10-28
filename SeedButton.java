import javax.swing.*;
import java.awt.*;

public class SeedButton extends JButton
{
    public Seed seed;
    public SeedButton[][] seedArr;
    public int currentState;
    public static ImageIcon SEED0_IMAGE = new ImageIcon("resources/Seed0.png");
    public static ImageIcon SEED1_IMAGE = new ImageIcon("resources/Seed1.png");
    public static ImageIcon SEED2_IMAGE = new ImageIcon("resources/Seed2.png");
    public static ImageIcon CONVERTED_SEED1_IMAGE = new ImageIcon("resources/Converted_Seed1.png");
    public static ImageIcon CONVERTED_SEED2_IMAGE = new ImageIcon("resources/Converted_Seed2.png");
    
    public SeedButton(Seed s, SeedButton[][] arr)
    {
        super();
        seed = s;
        seedArr = arr;
        currentState = seed.seedType;
        setIcon(SEED0_IMAGE);
    }
    
    public void scaleImage(int i)
    {
        SEED0_IMAGE = new ImageIcon(SEED0_IMAGE.getImage().getScaledInstance( i,i,  java.awt.Image.SCALE_SMOOTH ));
        SEED1_IMAGE = new ImageIcon(SEED1_IMAGE.getImage().getScaledInstance( i,i ,java.awt.Image.SCALE_SMOOTH ));
        SEED2_IMAGE = new ImageIcon(SEED2_IMAGE.getImage().getScaledInstance( i,i , java.awt.Image.SCALE_SMOOTH ));
        CONVERTED_SEED1_IMAGE = new ImageIcon(CONVERTED_SEED1_IMAGE.getImage().getScaledInstance( i,i, java.awt.Image.SCALE_SMOOTH ));
        CONVERTED_SEED2_IMAGE = new ImageIcon(CONVERTED_SEED2_IMAGE.getImage().getScaledInstance( i,i, java.awt.Image.SCALE_SMOOTH ));
    }
    
    public void update()
    {
        if(seed.getSeedType() == currentState)
        {
            return;
        }
        if(seed.getSeedType() == 0)
        {
            //this.setIcon(new ImageIcon(SEED0_IMAGE.getImage().getScaledInstance( this.getWidth(), this.getHeight(),  java.awt.Image.SCALE_SMOOTH ))  );
            setIcon(SEED0_IMAGE);
            currentState = 0;
            return;
        }
        if(seed.getSeedType() == 1)
        {
            setIcon(SEED1_IMAGE);
            if(currentState != 0)
            {
                setIcon(CONVERTED_SEED1_IMAGE);
            }
            currentState = 1;
            
            for(int r = seed.row - 1; r <= seed.row + 1; r++)
            {
                for(int c = seed.col - 1; c <= seed.col + 1 ; c++)
                {
                    if(seed.grid.withinBounds(r,c))
                        seedArr[r][c].update();
                }
            }
            return;
        }
        if(seed.getSeedType() == 2)
        {
            setIcon(SEED2_IMAGE);
            if(currentState !=0)
            {
                setIcon(CONVERTED_SEED2_IMAGE);
            }  
            currentState = 2;
            
            for(int r = seed.row - 1; r <= seed.row + 1; r++)
            {
                for(int c = seed.col - 1; c <= seed.col + 1 ; c++)
                {
                    if(seed.grid.withinBounds(r,c))
                        seedArr[r][c].update();
                }
            }
            return;
        }
    }
    
    public void updateAll()
    {
        for(SeedButton[] sa:seedArr)
        {
            for(SeedButton s:sa)
            {
                s.update();
            }
        }
    }
    

       
}