package BombSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class GameFrame extends JFrame {

    private BombFieldPanel fieldPanel;
    private ImageIcon resetButtonIcon;

    public GameFrame(int numRows, int numCols, int numBombs) {
        resetButtonIcon = new ImageIcon(getClass().getResource("/BombSweeper/resources/angry-smiley-icon.png"));


        setLayout(new BorderLayout(5, 5));
        initFrame();
        initGame(numRows, numCols, numBombs);
    }

    public void initGame(int numRows, int numCols, int numBombs) {
        if ((fieldPanel != null)) {
            remove(fieldPanel);
        }
        fieldPanel = new BombFieldPanel(numRows, numCols, numBombs, this);
        add(fieldPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initFrame() {
        JButton resetButton = new JButton(resetButtonIcon);
        resetButton.addActionListener(new ResetButtonListener());
        resetButton.setPreferredSize(new Dimension(31, 31));
        resetButton.setBackground(Color.WHITE);
        resetButton.setForeground(null);
        JPanel resetPanel = new JPanel();
        resetPanel.add(resetButton);
        add(resetPanel, BorderLayout.NORTH);

        setJMenuBar(new BombSweeperMenuBar(this));
        setTitle("Bomb Sweeper by Ramon Petgrave");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void resetGame() {
        fieldPanel.resetAll();
    }

    public void triggerWin(boolean win) {
        fieldPanel.revealBombsAndFlags();
        fieldPanel.disableAllCells();
        if (win) {
            JOptionPane.showMessageDialog(this, "You win!",
                    "BombSweeper", JOptionPane.PLAIN_MESSAGE);
        }

    }

    class ResetButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }
}
