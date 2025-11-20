package com.comp2042.model.game;

/**
 * Represents different game levels/modes.
 * Each level has unique gameplay mechanics.
 */
public enum GameLevel {
    /**
     * Classic Tetris mode - standard gameplay.
     */
    CLASSIC,

    /**
     * Lava Survival mode - lava descends from top, must clear 2 lines.
     */
    LAVA_SURVIVAL;

    /**
     * Gets the next level after completing current one.
     *
     * @return Next level in sequence
     */
    public GameLevel getNextLevel() {
        return switch (this) {
            case CLASSIC -> LAVA_SURVIVAL;
            case LAVA_SURVIVAL -> CLASSIC;
        };
    }

    /**
     * Gets number of lines required to advance to next level.
     *
     * @return Lines required
     */
    public int getLinesRequiredToAdvance() {
        return switch (this) {
            case CLASSIC -> 1;
            case LAVA_SURVIVAL -> 2;
        };
    }

    /**
     * Gets display name for the level.
     *
     * @return Level name
     */
    public String getDisplayName() {
        return switch (this) {
            case CLASSIC -> "Classic Mode";
            case LAVA_SURVIVAL -> "Lava Survival";
        };
    }
}