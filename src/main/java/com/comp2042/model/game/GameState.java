package com.comp2042.model.game;

/**
 * Represents the current state of the game.
 * Implements State Pattern for clean state management.
 */
public enum GameState {
    /**
     * Game is actively being played
     */
    PLAYING,

    /**
     * Game is paused
     */
    PAUSED,

    /**
     * Game has ended (game over)
     */
    GAME_OVER,

    /**
     * Game is in ready/menu state
     */
    READY;

    /**
     * Checks if the game state allows player input.
     */
    public boolean canAcceptInput() {
        return this == PLAYING;
    }

    /**
     * Checks if the game timer should be running.
     */
    public boolean isTimerActive() {
        return this == PLAYING;
    }

    /**
     * Checks if the game is finished.
     */
    public boolean isGameOver() {
        return this == GAME_OVER;
    }
}