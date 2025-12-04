package com.comp2042.model.game;

import java.awt.Point;
import java.util.*;

/**
 * Manages the Target Challenge level mechanics (Level 3).
 * Handles pre-filled block patterns, mission selection, countdown timer,
 * and win condition tracking. Players must clear all target blocks
 * within a 3-minute time limit.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class TargetChallengeManager {

    /**
     * Enumeration of available mission types in Target Challenge mode.
     * Each mission presents a unique block pattern to clear.
     */
    public enum MissionType {
        TOWER("Clear the Tower", "Tall stack in middle"),
        FRAME("Frame Buster", "Blocks around edges"),
        CHECKERBOARD("Checkerboard Chaos", "Alternating filled cells");

        private final String name;
        private final String description;

        MissionType(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
    }

    private static final int TIME_LIMIT_SECONDS = 180; // 3 minutes
    private static final Random random = new Random();

    private boolean active;
    private MissionType currentMission;
    private Set<Point> targetBlockPositions;
    private int remainingTargetBlocks;
    private long startTime;
    private int elapsedSeconds;
    private boolean missionComplete;

    /**
     * Creates a new TargetChallengeManager.
     */
    public TargetChallengeManager() {
        this.active = false;
        this.targetBlockPositions = new HashSet<>();
        this.missionComplete = false;
    }

    /**
     * Activates Target Challenge mode with a randomly selected mission.
     * Initializes timer and resets mission completion status.
     */
    public void activate() {
        active = true;
        missionComplete = false;
        selectRandomMission();
        startTime = System.currentTimeMillis();
        elapsedSeconds = 0;
    }

    /**
     * Deactivates Target Challenge mode and clears all state.
     * Resets target positions, mission selection, and completion flag.
     */
    public void deactivate() {
        active = false;
        targetBlockPositions.clear();
        currentMission = null;
        missionComplete = false;
    }

    /**
     * Selects a random mission type.
     */
    private void selectRandomMission() {
        MissionType[] missions = MissionType.values();
        currentMission = missions[random.nextInt(missions.length)];
    }

    /**
     * Generates the initial block pattern on the board based on current mission type.
     * Fills board cells with target blocks and records their positions.
     *
     * @param board The game board 2D array to fill with pattern
     * @param width Board height (number of rows)
     * @param height Board width (number of columns)
     */
    public void generatePattern(int[][] board, int width, int height) {
        targetBlockPositions.clear();

        switch (currentMission) {
            case TOWER -> generateTowerPattern(board, width, height);
            case FRAME -> generateFramePattern(board, width, height);
            case CHECKERBOARD -> generateCheckerboardPattern(board, width, height);
        }

        remainingTargetBlocks = targetBlockPositions.size();
    }

    /**
     * Generates a vertical tower pattern in the center columns.
     * Creates a 3-row tall, 3-block wide tower near the bottom of the board.
     * Uses color code 7 for target blocks.
     *
     * @param board The game board 2D array
     * @param width Board height (number of rows)
     * @param height Board width (number of columns)
     */
    private void generateTowerPattern(int[][] board, int width, int height) {
        int centerX = height / 2;
        int towerWidth = 3;
        int towerHeight = 3;  // Changed from 12 to 3 rows
        int startY = width - towerHeight - 2;  // Position it slightly above bottom

        for (int y = startY; y < startY + towerHeight; y++) {
            for (int x = centerX - towerWidth/2; x <= centerX + towerWidth/2; x++) {
                if (x >= 0 && x < height && y >= 0 && y < width) {
                    board[y][x] = 7; // Use color 7 for target blocks
                    targetBlockPositions.add(new Point(x, y));
                }
            }
        }
    }

    /**
     * Generates a frame pattern around the edges of the board.
     * Creates a border of blocks around the perimeter in the lower section.
     * Uses color code 7 for target blocks.
     *
     * @param board The game board 2D array
     * @param width Board height (number of rows)
     * @param height Board width (number of columns)
     */
    private void generateFramePattern(int[][] board, int width, int height) {
        int frameThickness = 2;
        int startY = width - 15; // Start from lower portion of board

        for (int y = startY; y < width; y++) {
            for (int x = 0; x < height; x++) {
                // Create frame on edges
                if (x < frameThickness || x >= height - frameThickness ||
                        y >= width - frameThickness) {
                    board[y][x] = 7;
                    targetBlockPositions.add(new Point(x, y));
                }
            }
        }
    }

    /**
     * Generates a checkerboard pattern of alternating filled cells.
     * Fills cells where (x + y) is even in the bottom 10 rows.
     * Uses color code 7 for target blocks.
     *
     * @param board The game board 2D array
     * @param width Board height (number of rows)
     * @param height Board width (number of columns)
     */
    private void generateCheckerboardPattern(int[][] board, int width, int height) {
        int startY = width - 10; // Bottom 10 rows

        for (int y = startY; y < width; y++) {
            for (int x = 0; x < height; x++) {
                // Create checkerboard pattern
                if ((x + y) % 2 == 0) {
                    board[y][x] = 7;
                    targetBlockPositions.add(new Point(x, y));
                }
            }
        }
    }

    /**
     * Updates the elapsed time and checks for timeout condition.
     * Calculates seconds elapsed since mission start.
     *
     * @return true if time limit (180 seconds) has been reached, false otherwise
     */
    public boolean updateTimer() {
        if (!active) return false;

        long currentTime = System.currentTimeMillis();
        elapsedSeconds = (int) ((currentTime - startTime) / 1000);

        return elapsedSeconds >= TIME_LIMIT_SECONDS;
    }

    /**
     * Checks if a cleared position was a target block.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if it was a target block
     */
    public boolean isTargetBlock(int x, int y) {
        return targetBlockPositions.contains(new Point(x, y));
    }

    /**
     * Updates the count of remaining target blocks after a clear.
     *
     * @param clearedPositions Set of cleared positions
     */
    public void updateTargetBlocks(Set<Point> clearedPositions) {
        if (!active) return;

        for (Point p : clearedPositions) {
            if (targetBlockPositions.remove(p)) {
                remainingTargetBlocks--;
            }
        }

        if (remainingTargetBlocks <= 0) {
            missionComplete = true;
        }
    }

    /**
     * Checks if all target blocks have been cleared.
     *
     * @return true if mission is complete
     */
    public boolean isMissionComplete() {
        return missionComplete && remainingTargetBlocks <= 0;
    }

    /**
     * Gets remaining time in seconds.
     *
     * @return Remaining seconds
     */
    public int getRemainingTime() {
        return Math.max(0, TIME_LIMIT_SECONDS - elapsedSeconds);
    }

    /**
     * Formats remaining time as MM:SS.
     *
     * @return Formatted time string
     */
    public String getFormattedTime() {
        int remaining = getRemainingTime();
        int minutes = remaining / 60;
        int seconds = remaining % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    // Getters
    public boolean isActive() { return active; }
    public MissionType getCurrentMission() { return currentMission; }
    public int getRemainingTargetBlocks() { return remainingTargetBlocks; }
    public Set<Point> getTargetBlockPositions() { return new HashSet<>(targetBlockPositions); }
}