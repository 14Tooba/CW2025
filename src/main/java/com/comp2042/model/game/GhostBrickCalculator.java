package com.comp2042.model.game;

import com.comp2042.utils.MatrixOperations;

import java.awt.Point;

/**
 * Utility class for calculating ghost brick landing positions.
 * The ghost brick shows players where the current piece will land
 * if dropped straight down without rotation or horizontal movement.
 * Follows Single Responsibility Principle by isolating position calculation logic.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class GhostBrickCalculator {

    /**
     * Calculates the landing position for a brick if dropped straight down.
     * Simulates brick falling until collision is detected with board bottom or existing blocks.
     *
     * @param gameMatrix Current game board state (2D array)
     * @param brickShape Current brick shape matrix (2D array)
     * @param currentPosition Current brick position as Point(x, y)
     * @return Point representing the landing position, or current position if inputs are null
     */
    public static Point calculateLandingPosition(int[][] gameMatrix, int[][] brickShape, Point currentPosition) {
        if (gameMatrix == null || brickShape == null || currentPosition == null) {
            return currentPosition;
        }

        Point ghostPosition = new Point(currentPosition);

        // Keep moving down until collision detected
        while (true) {
            Point nextPosition = new Point(ghostPosition);
            nextPosition.translate(0, 1);

            // Check if next position would cause collision
            boolean collision = MatrixOperations.intersect(
                    gameMatrix,
                    brickShape,
                    (int) nextPosition.getX(),
                    (int) nextPosition.getY()
            );

            if (collision) {
                // Current position is the landing spot
                return ghostPosition;
            }

            // Move to next position
            ghostPosition = nextPosition;
        }
    }
}