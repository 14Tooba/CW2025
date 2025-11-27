package com.comp2042.model.game;

import java.awt.Point;
import java.util.*;

/**
 * Manages the Target Challenge level mechanics.
 * Handles pre-filled block patterns, mission types, timer, and win conditions.
 */
public class TargetChallengeManager {

    /** Mission types available in Target Challenge */
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
     * Activates the Target Challenge with a random mission.
     */
    public void activate() {
        active = true;
        missionComplete = false;
        selectRandomMission();
        startTime = System.currentTimeMillis();
        elapsedSeconds = 0;
    }

    /**
     * Deactivates the Target Challenge.
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
     * Generates the initial pattern on the board based on mission type.
     *
     * @param board The game board matrix
     * @param width Board width
     * @param height Board height
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
     * Generates a tower pattern in the middle of the board.
     */
    private void generateTowerPattern(int[][] board, int width, int height) {
        int centerX = height / 2;
        int towerWidth = 3;
        int towerHeight = 12;
        int startY = width - towerHeight;

        for (int y = startY; y < width; y++) {
            for (int x = centerX - towerWidth/2; x <= centerX + towerWidth/2; x++) {
                if (x >= 0 && x < height && y >= 0) {
                    board[y][x] = 7; // Use color 7 for target blocks
                    targetBlockPositions.add(new Point(x, y));
                }
            }
        }
    }

    /**
     * Generates a frame pattern around the edges of the board.
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
     * Generates a checkerboard pattern.
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
     * Updates the timer and checks for timeout.
     *
     * @return true if time has run out
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