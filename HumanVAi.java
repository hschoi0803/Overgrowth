import java.util.*;
import java.io.*;
import java.net.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.awt.*;
import javax.swing.*;

public class HumanVAi extends UI implements Runnable
{
    public static int computerTurn;
    public SmartAi computer;
    Thread cp;

    public HumanVAi()
    {
        super();
        computerTurn = (int)(Math.random()*2) + 1;
        computer = new SmartAi(game,computerTurn);
    }

    public void initUI()
    {
        // the frame that contains the components
        frame = new JFrame("Overgrowth");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set the size of the frame
        //frame.setSize(1350, 1350);



        // set the rows and cols of the grid, as well the distances between them
        //GridLayout grid = new GridLayout(5, 3, 10, 10);
        GridLayout grid = new GridLayout(19, 19, 0 ,0);
        // what layout we want to use for our frame
        frame.setLayout(grid);

        // add a text field with a specified text to the frame
        JTextArea text = new JTextArea();
        text.setText("Result");
        text.setEditable(false);
        //frame.add(text);

        // add buttons to the frame
        //frame.add(new JButton("+"));
        //frame.add(new JButton("="));


        arrayBtn = new SeedButton[19][19];
        // add JButtons dynamically
        for(int r = 0; r < arrayBtn.length; r++)
        {
            for(int c = 0; c< arrayBtn[r].length; c++)
            {
                arrayBtn[r][c] = new SeedButton(game.get(r,c), arrayBtn);
                final int col = c;
                final int row = r+1;
                final SeedButton but = arrayBtn[r][c];
                arrayBtn[r][c].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    disableButtons();
                    System.out.println("" + (char)((int)'A' + col) + "" + row);
                    makeTurn("" + (char)((int)'A' + col) + "" + row);
                    but.update();
                }
                });
                arrayBtn[r][c].currentState=-1;
                frame.add(arrayBtn[r][c]);
            }
        }

        //frame.pack();

        /*
         try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        */


        //createMenuBar();

            frame.setVisible(true);
        JMenuBar menubar = new JMenuBar();
        ImageIcon icon = new ImageIcon("exit.png");

        JMenu file = new JMenu("Game");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit", icon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        JMenuItem hMenuItem = new JMenuItem("Help", icon);
        //eMenuItem.setMnemonic(KeyEvent.VK_E);
        hMenuItem.setToolTipText("show help");
        hMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //GridLayoutTest help = new GridLayoutTest();
            }
        });

        JMenuItem ngMenuItem = new JMenuItem("Start New Game", icon);
        //eMenuItem.setMnemonic(KeyEvent.VK_E);
        ngMenuItem.setToolTipText("starts new game of Overgrowth");
        final HumanVAi temp = this;
        ngMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                enableButtons();

                    growing = false;
                    try { Thread.sleep(1500); } catch (Exception e) {}


                makeTurn("reset");

                    computerTurn = (int)(Math.random()*2) + 1;
                    computer = new SmartAi(game,computerTurn);

                    cp = (new Thread(temp));
                    cp.start();

                for(int c = 0; c < arrayBtn.length; c++)
                {
                    for(SeedButton s: arrayBtn[c])
                    {
                        s.seed = game.get(s.seed.row,s.seed.col);
                        s.update();
                    }
                }
            }
        });

        JMenuItem undoMenuItem = new JMenuItem("Undo", icon);
        //eMenuItem.setMnemonic(KeyEvent.VK_E);
        undoMenuItem.setToolTipText("revert to previous board");
        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                makeTurn("undo");
                for(int c = 0; c < arrayBtn.length; c++)
                {
                    for(SeedButton s: arrayBtn[c])
                    {
                        s.seed = game.get(s.seed.row,s.seed.col);
                        s.update();
                    }
                }
            }
        });

        JMenuItem passMenuItem = new JMenuItem("Pass", icon);
        //eMenuItem.setMnemonic(KeyEvent.VK_E);
        passMenuItem.setToolTipText("pass the turn");
        passMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                disableButtons();
                makeTurn("pass");
                for(int c = 0; c < arrayBtn.length; c++)
                {
                    for(SeedButton s: arrayBtn[c])
                    {
                        s.seed = game.get(s.seed.row,s.seed.col);
                        s.update();
                    }
                }
            }
        });

            file.add(ngMenuItem);
            file.add(undoMenuItem);
            file.add(hMenuItem);
            file.add(passMenuItem);
            file.add(eMenuItem);

            menubar.add(file);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            System.out.println(frame.getSize());
            frame.setResizable(true);
            frame.setSize(new Dimension(screenSize.height, screenSize.height));
            frame.setSize(new Dimension(frame.getSize().height, frame.getSize().height));
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);

            arrayBtn[0][0].scaleImage(frame.getSize().height/19);
            arrayBtn[0][0].updateAll();

            frame.setJMenuBar(menubar);


    }

    public void play()
    {
        System.out.println("Starting OverGrowth!");
        System.out.println(game);
        System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
        cp = (new Thread(this));
        cp.start();
    }

    public void run()
    {
        while(this.growing)
        {
            if(playerTurn == computerTurn)
            {
                disableButtons();
                System.out.print("computer's turn: ");
                String move = computer.makeMove();
                System.out.println(move);
                makeTurn(move);
                if(move != "pass")
                    arrayBtn[Integer.parseInt(move.toString().substring(1))-1][move.toString().charAt(0) - (int)'A'].update();
                enableButtons();
            }
            else
            {
                enableButtons();
            }
            try { Thread.sleep(1000); } catch (Exception e) {}
        }
    }

    public static void begin()
    {

        HumanVAi game = new HumanVAi();
        game.play();
    }

    public static void main(String[] args)
    {
        HumanVAi.begin();
    }

}
