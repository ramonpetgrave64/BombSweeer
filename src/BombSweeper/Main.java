//BombSweeper version 1.1 by Ramon Petgrave, June 12, 2011.
package BombSweeper;

import javax.swing.*;

public class Main {

    public static final int DEFAULT_NUM_ROWS = 16;
    public static final int DEFAULT_NUM_COLS = 16;
    public static final int DEFAULT_NUM_BOMBS = 40;
    public static GameFrame frame;
    public static ImageIcon BOMB_ICON;
    public static ImageIcon FLAG_ICON;
    public static ImageIcon X_FLAG_ICON;
    public static ImageIcon[] NUMBER_ICONS;

    private Main() {
        initImageIcons();
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.run();
    }

    public void run() {
        int rows = DEFAULT_NUM_ROWS;
        int cols = DEFAULT_NUM_COLS;
        int numBombs = DEFAULT_NUM_BOMBS;

        frame = new GameFrame(rows, cols, numBombs);
    }

    private void initImageIcons() {
        BOMB_ICON = new ImageIcon(getClass().getResource("/BombSweeper/resources/bomb-icon2.png"));
        FLAG_ICON = new ImageIcon(getClass().getResource("/BombSweeper/resources/flag-red-icon.png"));
        X_FLAG_ICON = new ImageIcon(getClass().getResource("/BombSweeper/resources/x-flag-red-icon.png"));
        NUMBER_ICONS = new ImageIcon[9];

        for (int i = 1; i < 9; i++) {
            NUMBER_ICONS[i] = new ImageIcon(getClass().getResource("/BombSweeper/resources/"
                    + i + ".png"));
        }
    }
}