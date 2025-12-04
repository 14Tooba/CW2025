package com.comp2042.model.game;

import com.comp2042.constants.GameConstants;

/**
 * Manages the lava survival mechanic in Level 2.
 * Lava slowly descends from the top of the board.
 * Game ends if lava touches any placed blocks.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class LavaManager {

    private boolean active = false;
    private int lavaRow = -1; // Current row where lava bottom is located
    private long lastLavaMoveTime;
    private static final long LAVA_MOVE_INTERVAL_MS = 4000; // Lava moves every 4 seconds
    private int linesCleared = 0; // Track lines cleared in lava mode
    private static final int LAVA_THICKNESS = 3; //lava thickness

    /**
     * Activates lava mode.
     * Lava starts at the top and begins descending.
     */
    public void activate() {
        if (!active) {
            active = true;
            lavaRow = 0; // Start at top row
            lastLavaMoveTime = System.currentTimeMillis();
            linesCleared = 0;
        }
    }

    /**
     * Updates lava position based on elapsed time.
     * Moves lava down one row every LAVA_MOVE_INTERVAL_MS milliseconds (4 seconds).
     * Should be called regularly during gameplay.
     */
    public void update() {
        if (!active) return;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastLavaMoveTime >= LAVA_MOVE_INTERVAL_MS) {
            lavaRow++;
            lastLavaMoveTime = currentTime;
        }
    }


    /**
     * Checks if lava has collided with any placed blocks on the board.
     * Scans all rows occupied by lava for non-empty cells.
     *
     * @param boardMatrix The game board 2D array to check
     * @return true if lava touched a block (game over condition), false otherwise
     */
    public boolean checkLavaCollision(int[][] boardMatrix) {
        if (!active || lavaRow < 0) {
            return false;
        }

        // Check all lava rows (thickness)
        for (int row = lavaRow; row < lavaRow + LAVA_THICKNESS && row < boardMatrix.length; row++) {
            for (int col = 0; col < boardMatrix[row].length; col++) {
                if (boardMatrix[row][col] != GameConstants.COLOR_EMPTY) {
                    return true; // Lava hit a block!
                }
            }
        }
        return false;
    }


    /**
     * Gets all rows currently occupied by lava.
     * Lava fills from top (row 0) down to current lava row.
     *
     * @return Array of row indices covered by lava
     */
    public int[] getLavaRows() {
        if (!active || lavaRow < 0) {
            return new int[0];
        }

        // Return all rows from 0 to lavaRow (continuous lava from top)
        int[] rows = new int[lavaRow + LAVA_THICKNESS];
        for (int i = 0; i < rows.length && i < 25; i++) {
            rows[i] = i;
        }
        return rows;
    }


    /**
     * Records that a line was cleared in lava mode.
     */
    public void recordLineClear() {
        if (active) {
            linesCleared++;
        }
    }

    /**
     * Checks if player has cleared enough lines to complete lava level.
     *
     * @return true if 2 or more lines cleared
     */
    public boolean hasCompletedLavaLevel() {
        return linesCleared >= 2;
    }

    /**
     * Gets current lava row position.
     *
     * @return Row index where lava is currently at, -1 if inactive
     */
    public int getLavaRow() {
        return lavaRow;
    }

    /**
     * Checks if lava mode is currently active.
     *
     * @return true if lava is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Deactivates lava mode.
     */
    public void deactivate() {
        active = false;
        lavaRow = -1;
        linesCleared = 0;
    }

    /**
     * Resets lava manager for new game.
     */
    public void reset() {
        deactivate();
    }
}