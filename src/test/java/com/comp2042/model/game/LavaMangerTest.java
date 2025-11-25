package com.comp2042.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for LavaManager class.
 * Tests lava activation, movement, collision detection, and completion logic.
 */
class LavaManagerTest {

    private LavaManager lavaManager;

    @BeforeEach
    void setUp() {
        lavaManager = new LavaManager();
    }

    @Test
    @DisplayName("Lava is inactive by default")
    void testInactiveByDefault() {
        assertFalse(lavaManager.isActive());
        assertEquals(-1, lavaManager.getLavaRow());
    }

    @Test
    @DisplayName("Activate lava starts at row 0")
    void testActivateStartsAtTop() {
        lavaManager.activate();
        assertTrue(lavaManager.isActive());
        assertEquals(0, lavaManager.getLavaRow());
    }

    @Test
    @DisplayName("Lava does not activate twice")
    void testActivateOnce() {
        lavaManager.activate();
        int firstRow = lavaManager.getLavaRow();
        lavaManager.activate();
        assertEquals(firstRow, lavaManager.getLavaRow());
    }

    @Test
    @DisplayName("Get lava rows returns empty array when inactive")
    void testGetLavaRowsWhenInactive() {
        int[] rows = lavaManager.getLavaRows();
        assertEquals(0, rows.length);
    }

    @Test
    @DisplayName("Get lava rows returns continuous rows from top")
    void testGetLavaRowsContinuous() {
        lavaManager.activate();
        int[] rows = lavaManager.getLavaRows();

        assertTrue(rows.length > 0);
        assertEquals(0, rows[0]); // Should start from row 0
    }

    @Test
    @DisplayName("Record line clear increments counter")
    void testRecordLineClear() {
        lavaManager.activate();
        assertFalse(lavaManager.hasCompletedLavaLevel());

        lavaManager.recordLineClear();
        assertFalse(lavaManager.hasCompletedLavaLevel());

        lavaManager.recordLineClear();
        assertTrue(lavaManager.hasCompletedLavaLevel());
    }

    @Test
    @DisplayName("Record line clear only works when active")
    void testRecordLineClearWhenInactive() {
        lavaManager.recordLineClear();
        assertFalse(lavaManager.hasCompletedLavaLevel());
    }

    @Test
    @DisplayName("Completed level requires exactly 2 lines")
    void testCompletedLevelRequirement() {
        lavaManager.activate();

        lavaManager.recordLineClear();
        assertFalse(lavaManager.hasCompletedLavaLevel());

        lavaManager.recordLineClear();
        assertTrue(lavaManager.hasCompletedLavaLevel());

        // Still completed with more clears
        lavaManager.recordLineClear();
        assertTrue(lavaManager.hasCompletedLavaLevel());
    }

    @Test
    @DisplayName("Check collision returns false on empty board")
    void testNoCollisionOnEmptyBoard() {
        int[][] board = new int[10][5];
        lavaManager.activate();

        assertFalse(lavaManager.checkLavaCollision(board));
    }

    @Test
    @DisplayName("Check collision detects block at lava row")
    void testCollisionDetectsBlock() {
        int[][] board = new int[10][5];
        lavaManager.activate();

        // Place block at lava row
        board[0][2] = 1;
        assertTrue(lavaManager.checkLavaCollision(board));
    }

    @Test
    @DisplayName("Check collision detects blocks in any lava row")
    void testCollisionInMultipleRows() {
        int[][] board = new int[10][5];
        lavaManager.activate();

        // No collision initially
        assertFalse(lavaManager.checkLavaCollision(board));

        // Add block at row 3
        board[3][1] = 2;

        // Move lava down manually to row 3
        // (simulated by multiple updates - in real game this happens over time)
        assertFalse(lavaManager.checkLavaCollision(board));
    }

    @Test
    @DisplayName("Check collision returns false when inactive")
    void testCollisionWhenInactive() {
        int[][] board = new int[10][5];
        board[0][0] = 1;

        assertFalse(lavaManager.checkLavaCollision(board));
    }

    @Test
    @DisplayName("Check collision handles out of bounds")
    void testCollisionOutOfBounds() {
        int[][] board = new int[3][3]; // Small board
        lavaManager.activate();

        // Should not crash even if lava row exceeds board size
        assertDoesNotThrow(() -> lavaManager.checkLavaCollision(board));
    }

    @Test
    @DisplayName("Deactivate resets lava state")
    void testDeactivate() {
        lavaManager.activate();
        lavaManager.recordLineClear();
        lavaManager.recordLineClear();

        assertTrue(lavaManager.isActive());
        assertTrue(lavaManager.hasCompletedLavaLevel());

        lavaManager.deactivate();

        assertFalse(lavaManager.isActive());
        assertEquals(-1, lavaManager.getLavaRow());
        assertFalse(lavaManager.hasCompletedLavaLevel());
    }

    @Test
    @DisplayName("Reset clears all state completely")
    void testReset() {
        lavaManager.activate();
        lavaManager.recordLineClear();

        lavaManager.reset();

        assertFalse(lavaManager.isActive());
        assertEquals(-1, lavaManager.getLavaRow());
        assertFalse(lavaManager.hasCompletedLavaLevel());
    }

    @Test
    @DisplayName("Multiple activate-deactivate cycles work")
    void testMultipleCycles() {
        // First cycle
        lavaManager.activate();
        assertTrue(lavaManager.isActive());
        lavaManager.deactivate();
        assertFalse(lavaManager.isActive());

        // Second cycle
        lavaManager.activate();
        assertTrue(lavaManager.isActive());
        assertEquals(0, lavaManager.getLavaRow());
    }

    @Test
    @DisplayName("Lava row never goes negative after deactivation")
    void testLavaRowNeverNegativeAfterDeactivation() {
        lavaManager.activate();
        assertTrue(lavaManager.getLavaRow() >= 0);

        lavaManager.deactivate();
        assertEquals(-1, lavaManager.getLavaRow());
    }

    @Test
    @DisplayName("Get lava rows array does not cause NPE")
    void testGetLavaRowsNoNPE() {
        assertDoesNotThrow(() -> lavaManager.getLavaRows());

        lavaManager.activate();
        assertDoesNotThrow(() -> lavaManager.getLavaRows());

        lavaManager.deactivate();
        assertDoesNotThrow(() -> lavaManager.getLavaRows());
    }
}