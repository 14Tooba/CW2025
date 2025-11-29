package com.comp2042.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TargetChallengeManager.
 */
@DisplayName("Target Challenge Manager Tests")
public class TargetChallengeManagerTest {

    private TargetChallengeManager manager;
    private int[][] testBoard;
    private static final int WIDTH = 25;
    private static final int HEIGHT = 10;

    @BeforeEach
    void setUp() {
        manager = new TargetChallengeManager();
        testBoard = new int[WIDTH][HEIGHT];
    }

    @Test
    @DisplayName("Test initial state is inactive")
    void testInitialState() {
        assertFalse(manager.isActive(), "Manager should be inactive initially");
        assertNull(manager.getCurrentMission(), "Should have no mission initially");
        assertEquals(0, manager.getRemainingTargetBlocks(), "Should have 0 blocks initially");
        assertFalse(manager.isMissionComplete(), "Mission should not be complete initially");
    }

    @Test
    @DisplayName("Test activation sets up manager correctly")
    void testActivation() {
        manager.activate();

        assertTrue(manager.isActive(), "Manager should be active after activation");
        assertNotNull(manager.getCurrentMission(), "Should have a mission after activation");
        assertFalse(manager.isMissionComplete(), "Mission should not be complete on activation");
    }

    @Test
    @DisplayName("Test deactivation clears all state")
    void testDeactivation() {
        // First activate and generate pattern
        manager.activate();
        manager.generatePattern(testBoard, WIDTH, HEIGHT);
        assertTrue(manager.getRemainingTargetBlocks() > 0, "Should have target blocks after pattern generation");

        // Then deactivate
        manager.deactivate();

        assertFalse(manager.isActive(), "Manager should be inactive after deactivation");
        assertNull(manager.getCurrentMission(), "Should have no mission after deactivation");
        assertEquals(0, manager.getTargetBlockPositions().size(), "Should have no target positions after deactivation");
        assertFalse(manager.isMissionComplete(), "Mission should not be complete after deactivation");
    }

    @Test
    @DisplayName("Test Tower pattern generation")
    void testTowerPatternGeneration() {
        manager.activate();
        // Force tower pattern by activating multiple times until we get it
        while (manager.getCurrentMission() != TargetChallengeManager.MissionType.TOWER) {
            manager.activate();
        }

        manager.generatePattern(testBoard, WIDTH, HEIGHT);

        assertTrue(manager.getRemainingTargetBlocks() > 0, "Tower pattern should create blocks");
        assertTrue(hasBlocksInCenter(testBoard), "Tower pattern should have blocks in center");
    }

    @Test
    @DisplayName("Test Frame pattern generation")
    void testFramePatternGeneration() {
        manager.activate();
        // Force frame pattern
        while (manager.getCurrentMission() != TargetChallengeManager.MissionType.FRAME) {
            manager.activate();
        }

        manager.generatePattern(testBoard, WIDTH, HEIGHT);

        assertTrue(manager.getRemainingTargetBlocks() > 0, "Frame pattern should create blocks");
        assertTrue(hasBlocksOnEdges(testBoard), "Frame pattern should have blocks on edges");
    }

    @Test
    @DisplayName("Test Checkerboard pattern generation")
    void testCheckerboardPatternGeneration() {
        manager.activate();
        // Force checkerboard pattern
        while (manager.getCurrentMission() != TargetChallengeManager.MissionType.CHECKERBOARD) {
            manager.activate();
        }

        manager.generatePattern(testBoard, WIDTH, HEIGHT);

        assertTrue(manager.getRemainingTargetBlocks() > 0, "Checkerboard pattern should create blocks");
        // Checkerboard should have alternating pattern
        boolean foundPattern = false;
        for (int y = WIDTH - 10; y < WIDTH; y++) {
            for (int x = 0; x < HEIGHT - 1; x++) {
                if (testBoard[y][x] == 7 && testBoard[y][x + 1] != 7) {
                    foundPattern = true;
                    break;
                }
            }
        }
        assertTrue(foundPattern, "Checkerboard should have alternating pattern");
    }

    @Test
    @DisplayName("Test mission completion when all blocks cleared")
    void testMissionCompletion() {
        manager.activate();
        manager.generatePattern(testBoard, WIDTH, HEIGHT);

        assertFalse(manager.isMissionComplete(), "Mission should not be complete initially");

        // Clear all target blocks
        Set<Point> allTargets = manager.getTargetBlockPositions();
        manager.updateTargetBlocks(allTargets);

        assertTrue(manager.isMissionComplete(), "Mission should be complete after clearing all blocks");
        assertEquals(0, manager.getRemainingTargetBlocks(), "Should have 0 remaining blocks");
    }

    @Test
    @DisplayName("Test partial block clearing")
    void testPartialBlockClearing() {
        manager.activate();
        manager.generatePattern(testBoard, WIDTH, HEIGHT);

        int initialBlocks = manager.getRemainingTargetBlocks();

        // Clear only some blocks
        Set<Point> someTargets = new HashSet<>();
        int count = 0;
        for (Point p : manager.getTargetBlockPositions()) {
            someTargets.add(p);
            if (++count >= 3) break;  // Clear only 3 blocks
        }

        manager.updateTargetBlocks(someTargets);

        assertEquals(initialBlocks - 3, manager.getRemainingTargetBlocks(),
                "Should have 3 fewer blocks after clearing");
        assertFalse(manager.isMissionComplete(), "Mission should not be complete with blocks remaining");
    }

    @Test
    @DisplayName("Test timer functionality")
    void testTimerFunctionality() {
        manager.activate();

        assertFalse(manager.updateTimer(), "Timer should not be expired immediately");
        assertEquals(180, manager.getRemainingTime(), "Should start with 180 seconds");
        assertEquals("03:00", manager.getFormattedTime(), "Should format as 03:00 initially");

        // Test time formatting for different values
        // Note: In real implementation, we'd need to mock System.currentTimeMillis()
        assertTrue(manager.getRemainingTime() >= 0, "Remaining time should never be negative");
    }

    @Test
    @DisplayName("Test isTargetBlock method")
    void testIsTargetBlock() {
        manager.activate();
        manager.generatePattern(testBoard, WIDTH, HEIGHT);

        // Find a target block
        Point targetPos = null;
        for (int y = 0; y < WIDTH; y++) {
            for (int x = 0; x < HEIGHT; x++) {
                if (testBoard[y][x] == 7) {
                    targetPos = new Point(x, y);
                    break;
                }
            }
            if (targetPos != null) break;
        }

        if (targetPos != null) {
            assertTrue(manager.isTargetBlock(targetPos.x, targetPos.y),
                    "Should identify target block correctly");
        }

        // Test non-target position
        assertFalse(manager.isTargetBlock(-1, -1), "Should return false for invalid position");
        assertFalse(manager.isTargetBlock(0, 0), "Should return false for empty position (usually)");
    }

    @Test
    @DisplayName("Test time formatting edge cases")
    void testTimeFormattingEdgeCases() {
        manager.activate();

        // Test initial time
        assertEquals("03:00", manager.getFormattedTime());

        // The actual timer would need more complex testing with time mocking
        // but we can at least verify the format is correct
        String timeFormat = manager.getFormattedTime();
        assertTrue(timeFormat.matches("\\d{2}:\\d{2}"), "Time should be in MM:SS format");
    }

    // Helper methods
    private boolean hasBlocksInCenter(int[][] board) {
        int centerX = HEIGHT / 2;
        for (int y = WIDTH - 12; y < WIDTH; y++) {
            if (board[y][centerX] == 7) {
                return true;
            }
        }
        return false;
    }

    private boolean hasBlocksOnEdges(int[][] board) {
        // Check for blocks on edges
        for (int y = WIDTH - 15; y < WIDTH; y++) {
            if (board[y][0] == 7 || board[y][HEIGHT - 1] == 7) {
                return true;
            }
        }
        return false;
    }
}