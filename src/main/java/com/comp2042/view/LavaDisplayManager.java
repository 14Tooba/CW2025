package com.comp2042.view;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Manages visual display of lava in Lava Survival mode.
 * Handles lava row rendering and background atmosphere effects.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class LavaDisplayManager {

    private final GridPane gamePanel;
    private final Rectangle[][] displayMatrix;
    private boolean isLavaMode = false;

    /**
     * Creates a LavaDisplayManager with specified components.
     *
     * @param gamePanel The main game GridPane
     * @param displayMatrix The board display matrix
     */
    public LavaDisplayManager(GridPane gamePanel, Rectangle[][] displayMatrix) {
        this.gamePanel = gamePanel;
        this.displayMatrix = displayMatrix;
    }

    /**
     * Updates the visual rendering of lava on the game board.
     * Clears previous lava and fills current lava rows with bright orange.
     *
     * @param lavaRows Array of row indices currently occupied by lava
     */
    public void updateLavaDisplay(int[] lavaRows) {
        // Clear previous lava rendering
        for (int i = 2; i < displayMatrix.length; i++) {
            for (int j = 0; j < displayMatrix[i].length; j++) {
                Color currentColor = (Color) displayMatrix[i][j].getFill();
                if (currentColor.equals(Color.rgb(255, 69, 0)) ||
                        currentColor.equals(Color.rgb(255, 140, 0))) {
                    displayMatrix[i][j].setFill(Color.TRANSPARENT);
                }
            }
        }

        // Render all lava rows in bright orange
        for (int lavaRow : lavaRows) {
            if (lavaRow >= 2 && lavaRow < displayMatrix.length) {
                for (int j = 0; j < displayMatrix[lavaRow].length; j++) {
                    displayMatrix[lavaRow][j].setFill(Color.rgb(255, 69, 0));
                    displayMatrix[lavaRow][j].setArcHeight(9);
                    displayMatrix[lavaRow][j].setArcWidth(9);
                }
            }
        }
    }

    /**
     * Sets the background tint for lava mode atmosphere.
     * Applies subtle dark red tint when active.
     *
     * @param lavaActive true to apply lava background, false for normal
     */
    public void setLavaBackground(boolean lavaActive) {
        this.isLavaMode = lavaActive;

        if (lavaActive) {
            gamePanel.setStyle("-fx-background-color: rgba(40, 10, 10, 0.6);");
        } else {
            gamePanel.setStyle("-fx-background-color: transparent;");
        }
    }

    /**
     * Checks if lava mode is currently active.
     *
     * @return true if lava mode is active
     */
    public boolean isLavaMode() {
        return isLavaMode;
    }
}