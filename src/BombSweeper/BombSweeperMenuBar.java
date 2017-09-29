package BombSweeper;

import javax.swing.*;
import java.awt.event.*;

public class BombSweeperMenuBar extends JMenuBar {

    private JMenu gameMenu;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;
    private JMenuItem how_To_PlayMenuItem;
    private JMenuItem new_GameMenuItem;
    private JPopupMenu.Separator separator1;
    private JMenuItem beginnerMenuItem;
    private JMenuItem intermediateMenuItem;
    private JMenuItem expertMenuItem;
    private JPopupMenu.Separator separator2;
    private JMenuItem exitMenuItem;
    private final GameFrame frame;

    public BombSweeperMenuBar(GameFrame theFrame) {
        frame = theFrame;
        initComponents();
    }

    private void initComponents() {
        gameMenu = new JMenu();
        gameMenu.setName("Game");
        gameMenu.setText("Game");

        new_GameMenuItem = new JMenuItem();
        new_GameMenuItem.setName("New Game");
        new_GameMenuItem.setText("New Game");


        separator1 = new JPopupMenu.Separator();

        beginnerMenuItem = new JMenuItem();
        beginnerMenuItem.setName("Beginner");
        beginnerMenuItem.setText("Beginner");

        intermediateMenuItem = new JMenuItem();
        intermediateMenuItem.setName("Intermediate");
        intermediateMenuItem.setText("Intermediate");

        expertMenuItem = new JMenuItem();
        expertMenuItem.setName("Expert");
        expertMenuItem.setText("Expert");

        separator2 = new JPopupMenu.Separator();

        exitMenuItem = new JMenuItem();
        exitMenuItem.setName("Exit");
        exitMenuItem.setText("Exit");

        new_GameMenuItem.addActionListener(new New_GameListener());
        beginnerMenuItem.addActionListener(new BeginnerListener());
        intermediateMenuItem.addActionListener(new IntermediateListener());
        expertMenuItem.addActionListener(new ExpertListener());
        exitMenuItem.addActionListener(new ExitListener());

        gameMenu.add(new_GameMenuItem);
        gameMenu.add(separator1);
        gameMenu.add(beginnerMenuItem);
        gameMenu.add(intermediateMenuItem);
        gameMenu.add(expertMenuItem);
        gameMenu.add(separator2);
        gameMenu.add(exitMenuItem);

        add(gameMenu);

        helpMenu = new JMenu();
        helpMenu.setName("Help");
        helpMenu.setText("Help");

        aboutMenuItem = new JMenuItem();
        aboutMenuItem.setName("About");
        aboutMenuItem.setText("About");

        aboutMenuItem.addActionListener(new AboutListener());

        helpMenu.add(aboutMenuItem);
        
        how_To_PlayMenuItem = new JMenuItem();
        how_To_PlayMenuItem.setName("How To Play");
        how_To_PlayMenuItem.setText("How To Play");
        
        how_To_PlayMenuItem.addActionListener(new How_To_PlayListener());
        
        helpMenu.add(how_To_PlayMenuItem);
        
        add(helpMenu);
    }

    class New_GameListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            frame.resetGame();
        }
    }

    class BeginnerListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            frame.initGame(8, 8, 10);
        }
    }

    class IntermediateListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            frame.initGame(16, 16, 40);
        }
    }

    class ExpertListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            frame.initGame(16, 30, 99);
        }
    }

    class ExitListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }

    class AboutListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "This is a simple game of"
                    + " MineSweeper." + "\n\n" + "created by Ramon Petgrave",
                    "About", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    class How_To_PlayListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String howtoplay = "The idea is simple:"
                    + "\n\n" + ""
                    + "Their is a gridded bombfield. Some cells in the grid have"
                    + " \n" + "bombs underneath and some don't."
                    + "\n\n"
                    + "You have to uncover all the cells that do NOT have bomb,"
                    + "\n" + "while avoiding uncovering cells that DO have bomb."
                    + "\n\n" + "To uncover a cell, simply left-click on it. If their is a"
                    + "\n" + "number displayed under that cell, then their are that many cells"
                    + "\n" + "with bombs directly adjacent to that same uncovered cell. Using logic"
                    + "\n" + "you can discern which cells have bomb and which cells dont."
                    + "\n\n" + "You can left-click to \"flag\" a cell; mark a cell"
                    + "\n" + "that you believe their to be a bomb underneath."
                    + "\n\n" + "The number of flags, Y, from \"X / Y flags\" tells you exactly how"
                    + "many bombs there are in the field.";
            
            JOptionPane.showMessageDialog(frame, howtoplay, "How To Play",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
}
