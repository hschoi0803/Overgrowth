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


public class UI extends PublicTester {
    //private static JButton[] arrayBtn;
    //private static Thread currentGame;
    public JFrame frame;
    public SeedButton[][] arrayBtn;
    public JLabel headerLabel = new JLabel("This is where the status goes",JLabel.CENTER );
    //protected BufferedReader in;

    public UI()
    {
        super();
        initUI();
    }

    public void initUI()
    {

        // the frame that contains the components
        frame = new JFrame("Overgrowth");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setBackground(Color.black);
        // set the size of the frame
        //frame.setSize(1350, 1350);

        //frame.add(headerLabel);

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

        //frame.add(new JTextField());
        /*
        for(int i = 0; i < 19; i++)
        {
            JTextField t = new JTextField();
            t.setText("" +(char)((int)'A' + i));
            t.setHorizontalAlignment(JTextField.CENTER);
            t.setEditable(false);
            frame.add(t);
        }
        */
       /*
        JTextField line = new JTextField();
        line.setEditable(false);
        frame.add(line);
        */

        for(int r = 0; r < arrayBtn.length; r++)
        {
            /*
            JTextField t = new JTextField();
            int num = r+1;
            t.setText("" + num);
            t.setHorizontalAlignment(JTextField.CENTER);
            t.setEditable(false);
            frame.add(t);
            */
            for(int c = 0; c< arrayBtn[r].length; c++)
            {

                arrayBtn[r][c] = new SeedButton(game.get(r,c), arrayBtn);
                final int col = c;
                final int row = r+1;
                final SeedButton but = arrayBtn[r][c];
                arrayBtn[r][c].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    System.out.println("" + (char)((int)'A' + col) + "" + row);
                    makeTurn("" + (char)((int)'A' + col) + "" + row);
                    but.update();
                }
                });
                arrayBtn[r][c].currentState=-1;
                frame.add(arrayBtn[r][c]);
            }
            /*
            t = new JTextField();
            t.setEditable(false);
            frame.add(t);
            */
        }

        /*
        for(int i = 0; i < 21; i++)
        {
            JTextField t = new JTextField();
            t.setEditable(false);
            frame.add(t);
        }
        */

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
        ngMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                makeTurn("reset");
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
            // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            // int length = Math.max(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            // System.out.println("length: " + length);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int length = Math.min((int)(screenSize.getWidth()*0.8),
                                  (int)(screenSize.getHeight()*0.8));
            frame.setSize(length, length);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);

            arrayBtn[0][0].scaleImage(frame.getSize().height/19);
            arrayBtn[0][0].updateAll();

            frame.setJMenuBar(menubar);




    }

    public void endGame()
    {
        growing = false;
        int[] score = game.calculatePoints();
        JFrame endFrame = new JFrame("Results");
        JFrame.setDefaultLookAndFeelDecorated(true);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setLayout(new GridLayout(3, 2));
        endFrame.add(new JLabel("Player 1 score:"));
        endFrame.add(new JLabel("" + score[0]));
        endFrame.add(new JLabel("Player 2 score: "));
        endFrame.add(new JLabel("" + score[1]));

        int frameWidth = 200;
        int frameHeight = 100;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        endFrame.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);
        endFrame.setResizable(false);
        endFrame.setLocationRelativeTo(null);
        endFrame.setVisible(true);
    }

    public void enableButtons()
    {
        /*
        for(int c = 0; c < this.arrayBtn.length; c++)
        {
            for(SeedButton s : arrayBtn[c])
            {
                s.setEnabled(true);
            }
        }
        */

    }

    public void disableButtons()
    {
        /*
        for(int c = 0; c < this.arrayBtn.length; c++)
        {
            for(SeedButton s : arrayBtn[c])
            {
                s.setEnabled(false);
            }
        }
        */

    }

    public void play()
    {
        System.out.println("Starting OverGrowth!");
        System.out.println(game);
        System.out.println("(Turn " + numTurns + ")Player " + playerTurn + "'s turn: ");
    }


    public static void begin()
    {

        UI game = new UI();
        // game.play();
    }

    public static void main(String[] args)
    {
        begin();
    }
}
