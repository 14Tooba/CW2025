package com.comp2042.model.game;

import com.comp2042.constants.GameConstants;

/**
 * Manages the lava survival mechanic in Level 2.
 * Lava slowly descends from the top of the board.
 * Game ends if lava touches any placed blocks.
 *
 */
public class LavaManager {

    private boolean active = false;
    private int lavaRow = -1; // Current row where lava bottom is located
    private long lastLavaMoveTime;
    private static final long LAVA_MOVE_INTERVAL_MS = 4000; // Lava moves every 4 seconds
    private int linesCleared = 0; // Track lines cleared in lava mode

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
     * Lava moves down one row every LAVA_MOVE_INTERVAL_MS milliseconds.
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
     * Checks if lava has collided with any placed blocks.
     *
     * @param boardMatrix The game board matrix
     * @return true if lava touched a block (game over), false otherwise
     */
    public boolean checkLavaCollision(int[][] boardMatrix) {
        if (!active || lavaRow < 0 || lavaRow >= boardMatrix.length) {
            return false;
        }

        // Check if current lava row has any non-empty cells
        for (int col = 0; col < boardMatrix[lavaRow].length; col++) {
            if (boardMatrix[lavaRow][col] != GameConstants.COLOR_EMPTY) {
                return true; // Lava hit a block!
            }
        }
        return false;
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