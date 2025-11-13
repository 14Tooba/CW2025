package com.comp2042.model.game;

import com.comp2042.utils.MatrixOperations;

import java.awt.Point;

/**
 * Calculates ghost brick position (where brick will land).
 * Follows Single Responsibility Principle.
 */
public class GhostBrickCalculator {

    /**
     * Calculates the landing position for a brick if it drops straight down.
     *
     * @param gameMatrix Current game board state
     * @param brickShape Current brick shape matrix
     * @param currentPosition Current brick position
     * @return Point representing where brick will land
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