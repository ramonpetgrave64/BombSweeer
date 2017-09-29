package BombSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BombFieldPanel extends JPanel {

    public final int NUM_ROWS;
    public final int NUM_COLS;
    public final int NUM_BOMBS;
    private int flagsUsed;
    private int cellsRevealed;
    private JLabel flagLabel;
    public final ArrayList<Cell> CELL_LIST;
    public final ArrayList<Cell> BOMB_LIST;
    public final Dimension CELL_PREFERRED_SIZE;
    public final JPanel fieldPanel;
    public final JPanel scorePanel;
    public final GameFrame frame;

    public BombFieldPanel(int numRows, int numCols, int numBombs, GameFrame frame) {
        setLayout(new BorderLayout(5, 5));
        NUM_ROWS = numRows;
        NUM_COLS = numCols;
        NUM_BOMBS = numBombs;
        this.frame = frame;
        CELL_LIST = new ArrayList<Cell>();
        BOMB_LIST = new ArrayList<Cell>();
        fieldPanel = new JPanel();
        add(fieldPanel, BorderLayout.CENTER);
        scorePanel = new JPanel();
        add(scorePanel, BorderLayout.NORTH);

        CELL_PREFERRED_SIZE = new Dimension(28, 28);

        initField(numRows, numCols);
        initBombs(numBombs);
        initScorer(numBombs);
    }

    public void resetAll() {
        for (Cell cell : CELL_LIST) {
            cell.reset();
        }

        flagsUsed = 0;
        cellsRevealed = 0;
        updateScorer();
        initBombs(NUM_BOMBS);
    }

    private void initScorer(int numBombs) {
        scorePanel.setLayout(new BorderLayout(5, 5));
        flagLabel = new JLabel();
        flagLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateScorer();
        scorePanel.add(flagLabel, BorderLayout.CENTER);
    }

    public void updateScorer() {
        flagLabel.setText("   " + flagsUsed + " / " + NUM_BOMBS + " flags");
    }

    private void initField(int rows, int cols) {
        fieldPanel.setLayout(new GridLayout(rows, cols));

        int n = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                Cell newCell = new Cell(i, j);
                newCell.addMouseListener(new CellClickListener());
                newCell.addActionListener(new ClickActionListener());
                newCell.setPreferredSize(CELL_PREFERRED_SIZE);
                CELL_LIST.add(newCell);
                fieldPanel.add(newCell, n);
                n++;
            }
        }
    }

    public Cell getCell(int row, int col) {
        int n = ((row - 1) * NUM_COLS) + col - 1;

        return CELL_LIST.get(n);
    }

    private void initBombs(int numBombs) {
        BOMB_LIST.clear();
        int rndRow = 0;
        int rndCol = 0;
        int num = 0;

        while (num < numBombs) {
            rndRow = (int) (Math.random() * NUM_ROWS) + 1;
            rndCol = (int) (Math.random() * NUM_COLS) + 1;
            Cell newBomb = getCell(rndRow, rndCol);

            if (!newBomb.hasBomb()) {
                newBomb.hasBomb(true);
                BOMB_LIST.add(newBomb);
                num++;
            }
        }
    }

    public boolean checkIfBomb(Cell theCell) {
        return theCell.hasBomb();
    }

    public ArrayList<Cell> getAdjacentCells(Cell theCell) {
        ArrayList<Cell> adjCells = new ArrayList<Cell>();
        int cellRow = theCell.row;
        int cellCol = theCell.col;
        int num = 0;

        for (int i = cellRow - 1; i <= cellRow + 1; i++) {
            for (int j = cellCol - 1; j <= cellCol + 1; j++) {
                if (i == theCell.row && j == theCell.col) {
                    continue;
                } else {
                    if (cellIsInBounds(i, j)) {
                        adjCells.add(getCell(i, j));
                        num++;
                    }
                }
            }
        }
        return adjCells;
    }

    public boolean cellIsInBounds(int row, int col) {
        if (row < 1 || row > NUM_ROWS) {
            return false;
        } else if (col < 1 || col > NUM_COLS) {
            return false;
        }
        return true;
    }

    public boolean cellIsInBounds(Cell theCell) {
        return cellIsInBounds(theCell.row, theCell.col);
    }

    public void revealBombsAndFlags() {
        for (Cell cell : CELL_LIST) {
            if (checkIfBomb(cell)) {
                revealCell(cell);
                cell.setBackground(Color.LIGHT_GRAY);
            }
            if (cell.isFlagged() && !checkIfBomb(cell)) {
                revealCell(cell);
                cell.setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    public int numAdjacentBombs(Cell theCell) {
        ArrayList<Cell> adjacentCells = getAdjacentCells(theCell);
        int num = 0;

        for (Cell adjCell : adjacentCells) {
            if (checkIfBomb(adjCell) == true) {
                num++;
            }
        }
        return num;
    }

    public int checkCell(Cell theCell) {
        if (checkIfBomb(theCell)) {
            return -1;
        } else {
            return numAdjacentBombs(theCell);
        }
    }

    public void revealCell(Cell theCell) {
        if (!theCell.isRevealed()) {
            theCell.reveal(checkCell(theCell));
            cellsRevealed++;
        }
    }

    public void revealAllCells() {
        for (Cell cell : CELL_LIST) {
            revealCell(cell);
        }
    }

    public void disableAllCells() {
        for (Cell cell : CELL_LIST) {
            cell.setEnabled(false);
        }
    }

    public void triggerCellFlag(Cell theCell) {
        if (!theCell.isRevealed()) {
            if (!theCell.isFlagged()) {
                theCell.isFlagged(true);
                flagsUsed++;
            } else {
                theCell.isFlagged(false);
                flagsUsed--;
            }
            updateScorer();
        }
    }

    public void revealConcurrentBlanks(Cell theCell) {
        ArrayList<Cell> adjacentCells = getAdjacentCells(theCell);

        for (Cell adjCell : adjacentCells) {
            if (!adjCell.isRevealed()) {
                revealCell(adjCell);
                if (checkCell(adjCell) == 0) {
                    revealConcurrentBlanks(adjCell);
                }
            }
        }
    }

    public boolean isWin() {
        return (NUM_ROWS * NUM_COLS - cellsRevealed == NUM_BOMBS);
    }

    class CellClickListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                triggerCellFlag((Cell) (e.getSource()));
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    public void revealAllBombs() {
        for (Cell bomb : BOMB_LIST) {
            revealCell(bomb);
            bomb.setBackground(Color.LIGHT_GRAY);
        }
    }

    class ClickActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Cell clickedCell = (Cell) (e.getSource());

            if (!clickedCell.isFlagged()) {
                int cellResult = checkCell(clickedCell);
                revealCell(clickedCell);

                if (cellResult == -1) {
                    frame.triggerWin(false);
                } else if (cellResult == 0) {
                    revealConcurrentBlanks(clickedCell);
                }
                if (isWin()) {
                    frame.triggerWin(true);
                }
            }
        }
    }
}