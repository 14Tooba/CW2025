package com.comp2042.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Game over notification panel displayed when game ends.
 * Shows "GAME OVER" message with appropriate styling.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class GameOverPanel extends BorderPane {

    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        setCenter(gameOverLabel);
    }

}
