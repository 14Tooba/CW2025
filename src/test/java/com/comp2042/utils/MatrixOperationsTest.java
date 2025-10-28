package com.comp2042.utils;

import com.comp2042.ClearRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    private int[][] emptyBoard;
    private int[][] testBrick;

    @BeforeEach
    void setUp() {
        // Create a small test board (5x5)
        emptyBoard = new int[5][5];

        // Create a simple L-shaped brick
        testBrick = new int[][]{
                {1, 1},
                {1, 0},
        };
    }

    @Test
    @DisplayName("Test brick doesn't collide in empty board")
    void testIntersect_ValidPosition() {
        boolean collision = MatrixOperations.intersect(emptyBoard, testBrick, 0, 0);
        assertFalse(collision, "Brick should not collide in empty board");
    }

    @Test
    @DisplayName("Test brick collides when out of bounds")
    void testIntersect_OutOfBounds() {
        boolean collision = MatrixOperations.intersect(emptyBoard, testBrick, -1, 0);
        assertTrue(collision, "Brick should collide when out of bounds");
    }

    @Test
    @DisplayName("Test copy creates independent copy")
    void testCopy_Independent() {
        int[][] original = {{1, 2}, {3, 4}};
        int[][] copy = MatrixOperations.copy(original);

        // Modify copy
        copy[0][0] = 99;

        // Original should be unchanged
        assertEquals(1, original[0][0]);
        assertEquals(99, copy[0][0]);
    }

    @Test
    @DisplayName("Test clearing single complete row")
    void testCheckRemoving_SingleRow() {
        int[][] board = new int[5][5];

        // Fill bottom row
        for (int j = 0; j < 5; j++) {
            board[4][j] = 1;
        }

        ClearRow result = MatrixOperations.checkRemoving(board);

        assertEquals(1, result.getLinesRemoved());
        assertEquals(50, result.getScoreBonus());
    }

    @Test
    @DisplayName("Test clearing multiple rows - quadratic scoring")
    void testCheckRemoving_MultipleRows() {
        int[][] board = new int[5][5];

        // Fill bottom TWO rows
        for (int i = 3; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = 1;
            }
        }

        ClearRow result = MatrixOperations.checkRemoving(board);

        assertEquals(2, result.getLinesRemoved());
        assertEquals(200, result.getScoreBonus()); // 50 * 2^2 = 200
    }

    @Test
    @DisplayName("Test no complete rows returns zero")
    void testCheckRemoving_NoCompleteRows() {
        int[][] board = new int[5][5];

        // Partially fill a row (leave one empty)
        for (int j = 0; j < 4; j++) {
            board[4][j] = 1;
        }

        ClearRow result = MatrixOperations.checkRemoving(board);

        assertEquals(0, result.getLinesRemoved());
        assertEquals(0, result.getScoreBonus());
    }
}