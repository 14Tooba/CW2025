package com.comp2042.constants;

/**
 * Central repository for all game constants.
 * Eliminates magic numbers and provides single source of truth.
 */
public final class GameConstants {

    // Prevent instantiation
    private GameConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }

    // Board dimensions
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 25;
    public static final int VISIBLE_BOARD_HEIGHT = 23;

    // Visual constants
    public static final int BRICK_SIZE = 20;
    public static final double BRICK_ARC_SIZE = 9.0;
    public static final int GRID_GAP = 1;

    // Game timing
    public static final int DEFAULT_FALL_SPEED_MS = 400;
    public static final int FAST_FALL_SPEED_MS = 50;
    public static final int NOTIFICATION_DURATION_MS = 2500;

    // Scoring
    public static final int SCORE_PER_SOFT_DROP = 1;
    public static final int SCORE_BASE_MULTIPLIER = 50;

    // Brick spawn position
    public static final int BRICK_SPAWN_X = 4;
    public static final int BRICK_SPAWN_Y = 0; //changed spawn location of brick

    // Animation constants
    public static final double NOTIFICATION_FADE_START = 1.0;
    public static final double NOTIFICATION_FADE_END = 0.0;
    public static final double NOTIFICATION_MOVE_DISTANCE = -40;
    public static final double GLOW_EFFECT_LEVEL = 0.6;

    // Reflection effect
    public static final double REFLECTION_FRACTION = 0.8;
    public static final double REFLECTION_TOP_OPACITY = 0.9;
    public static final double REFLECTION_TOP_OFFSET = -12;

    // Brick colors
    public static final int COLOR_EMPTY = 0;
    public static final int COLOR_I_BRICK = 1;
    public static final int COLOR_J_BRICK = 2;
    public static final int COLOR_L_BRICK = 3;
    public static final int COLOR_O_BRICK = 4;
    public static final int COLOR_S_BRICK = 5;
    public static final int COLOR_T_BRICK = 6;
    public static final int COLOR_Z_BRICK = 7;

    // Window dimensions
    public static final int WINDOW_WIDTH = 300;
    public static final int WINDOW_HEIGHT = 510;
    public static final String WINDOW_TITLE = "TetrisJFX";

    // File paths
    public static final String FXML_LAYOUT = "gameLayout.fxml";
    public static final String CSS_STYLE = "window_style.css";
    public static final String FONT_DIGITAL = "digital.ttf";

    // Brick matrix size
    public static final int BRICK_MATRIX_SIZE = 4;
}