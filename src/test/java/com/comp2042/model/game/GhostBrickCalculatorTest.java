package com.comp2042.model.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GhostBrickCalculator.
 */
class GhostBrickCalculatorTest {

    @Test
    @DisplayName("Ghost lands at bottom of empty board")
    void testEmptyBoard() {
        int[][] board = new int[10][5];
        int[][] brick = {{1}};
        Point current = new Point(2, 0);

        Point landing = GhostBrickCalculator.calculateLandingPosition(board, brick, current);

        assertEquals(2, landing.x);
        assertEquals(9, landing.y);
    }

    @Test
    @DisplayName("Ghost stops above obstacle")
    void testWithObstacle() {
        int[][] board = new int[10][5];
        board[8][2] = 1;

        int[][] brick = {{1}};
        Point current = new Point(2, 0);

        Point landing = GhostBrickCalculator.calculateLandingPosition(board, brick, current);

        assertEquals(2, landing.x);
        assertEquals(7, landing.y);
    }

    @Test
    @DisplayName("Ghost handles null input safely")
    void testNullInput() {
        Point current = new Point(2, 2);

        Point result1 = GhostBrickCalculator.calculateLandingPosition(null, new int[][]{{1}}, current);
        Point result2 = GhostBrickCalculator.calculateLandingPosition(new int[10][5], null, current);
        Point result3 = GhostBrickCalculator.calculateLandingPosition(new int[10][5], new int[][]{{1}}, null);

        assertEquals(current, result1);
        assertEquals(current, result2);
        assertNull(result3);
    }

    @Test
    @DisplayName("Ghost equals current position when already at bottom")
    void testAlreadyAtBottom() {
        int[][] board = new int[5][5];
        int[][] brick = {{1}};
        Point current = new Point(2, 4);

        Point landing = GhostBrickCalculator.calculateLandingPosition(board, brick, current);

        assertEquals(current.x, landing.x);
        assertEquals(current.y, landing.y);
    }

    @Test
    @DisplayName("Ghost handles L-shaped brick")
    void testLShapedBrick() {
        int[][] board = new int[10][5];
        int[][] brick = {
                {1, 1},
                {1, 0}
        };
        Point current = new Point(1, 0);

        Point landing = GhostBrickCalculator.calculateLandingPosition(board, brick, current);

        assertNotNull(landing);
        assertTrue(landing.y >= current.y);
        assertTrue(landing.y < board.length);
    }

    @Test
    @DisplayName("Ghost stops on top of filled bottom row")
    void testFilledBottom() {
        int[][] board = new int[10][5];
        for (int j = 0; j < 5; j++) {
            board[9][j] = 1;
        }

        int[][] brick = {{1}};
        Point current = new Point(2, 0);

        Point landing = GhostBrickCalculator.calculateLandingPosition(board, brick, current);

        assertEquals(2, landing.x);
        assertEquals(8, landing.y);
    }

    @Test
    @DisplayName("Ghost X position stays same as current")
    void testXPositionUnchanged() {
        int[][] board = new int[10][5];
        int[][] brick = {{1}};
        Point current = new Point(3, 2);

        Point landing = GhostBrickCalculator.calculateLandingPosition(board, brick, current);

        assertEquals(current.x, landing.x);
    }

    @Test
    @DisplayName("Ghost Y is always greater or equal to current Y")
    void testYAlwaysDownward() {
        int[][] board = new int[10][5];
        int[][] brick = {{1}};

        for (int startY = 0; startY < 8; startY++) {
            Point current = new Point(2, startY);
            Point landing = GhostBrickCalculator.calculateLandingPosition(board, brick, current);

            assertTrue(landing.y >= current.y,
                    "Ghost Y should be >= current Y. Start: " + startY + ", Landing: " + landing.y);
        }
    }
}