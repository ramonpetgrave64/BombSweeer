package BombSweeper;

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {

    private int value;
    public final int row;
    public final int col;
    private boolean isRevealed;
    private boolean hasBomb;
    private boolean isFlagged;
    public final ImageIcon BOMB_ICON = Main.BOMB_ICON;
    public final ImageIcon FLAG_ICON = Main.FLAG_ICON;
    public final ImageIcon X_FLAG_ICON = Main.X_FLAG_ICON;
    public ImageIcon[] NUMBER_ICONS = Main.NUMBER_ICONS;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        isRevealed = false;
        hasBomb = false;
        isFlagged = false;
        setBackground(Color.LIGHT_GRAY);
    }

    public void isFlagged(boolean flag) {
        if (flag) {
            if (!isRevealed) {
                isFlagged = true;
                setIcon(FLAG_ICON);

            }
        } else {
            setIcon(null);
            isFlagged = false;
        }
        updateUI();
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void hasBomb(boolean bomb) {
        hasBomb = bomb;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void reveal(int adjacentBombs) {
        if (!hasBomb && isFlagged) {
            setIcon(X_FLAG_ICON);
            setDisabledIcon(X_FLAG_ICON);
        } else if (hasBomb) {
            setIcon(BOMB_ICON);
            setDisabledIcon(BOMB_ICON);
        } else if (adjacentBombs > 0) {
            setIcon(NUMBER_ICONS[adjacentBombs]);
            setDisabledIcon(NUMBER_ICONS[adjacentBombs]);
        } else if (adjacentBombs == 0 && isFlagged) {
            setIcon(X_FLAG_ICON);
            setDisabledIcon(X_FLAG_ICON);
        }
        isRevealed = true;
        setBackground(Color.WHITE);
        updateUI();
        setEnabled(false);
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean equals(Cell theCell) {
        if (theCell.row == this.row && theCell.col == this.col) {
            return true;
        }
        return false;
    }

    public void reset() {
        isRevealed = false;
        hasBomb = false;
        isFlagged = false;

        setIcon(null);
        setDisabledIcon(null);
        setBackground(Color.LIGHT_GRAY);
        setEnabled(true);
    }

    @Override
    public String toString() {
        return ("(" + row + ", " + col + ")");
    }
}