package com.comp2042.view.GUI;

import com.comp2042.view.RectangleRenderer;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import com.comp2042.constants.GameConstants;

/**
 * Handles rendering and updating the game board background.
 * Manages the display matrix of rectangles representing the static game state.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class GameBoardRenderer {

    private static final int BRICK_SIZE = GameConstants.BRICK_SIZE;
    private Rectangle[][] displayMatrix;
    private final GridPane gamePanel;

    /**
     * Creates a GameBoardRenderer for the specified GridPane.
     *
     * @param gamePanel The GridPane where the board will be rendered
     */
    public GameBoardRenderer(GridPane gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Initializes the display matrix with rectangles for each board cell.
     * Creates rectangles starting from row 2 (hiding top spawn rows).
     *
     * @param boardMatrix The game board state matrix
     */
    public void initializeBoard(int[][] boardMatrix) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(javafx.scene.paint.Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }
    }

    /**
     * Refreshes the game board display based on current board state.
     * Updates colors of all rectangles to match the board matrix.
     *
     * @param board Current game board matrix (2D array)
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                styleRectangle(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * Applies color and styling to a rectangle based on color code.
     *
     * @param colorCode The color code to apply
     * @param rectangle The rectangle to style
     */
    private void styleRectangle(int colorCode, Rectangle rectangle) {
        RectangleRenderer.styleRectangle(rectangle, colorCode);
    }

    /**
     * Gets the display matrix of rectangles.
     *
     * @return 2D array of Rectangle objects
     */
    public Rectangle[][] getDisplayMatrix() {
        return displayMatrix;
    }
}