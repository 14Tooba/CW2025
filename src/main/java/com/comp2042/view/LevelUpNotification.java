package com.comp2042.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Displays "LEVEL UP!" notification when player advances levels.
 */
public class LevelUpNotification extends StackPane {

    public LevelUpNotification(String levelName) {
        // Bright red background so it's impossible to miss
        setStyle("-fx-background-color: rgba(255, 0, 0, 0.95);");

        VBox container = new VBox(30);
        container.setAlignment(Pos.CENTER);

        // "LEVEL UP!" - huge white text on black background
        Label levelUpLabel = new Label("LEVEL UP!");
        levelUpLabel.setStyle(
                "-fx-font-size: 64px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: WHITE;" +
                        "-fx-background-color: BLACK;" +
                        "-fx-padding: 20;"
        );

        // Level name - yellow text on black background
        Label levelNameLabel = new Label(levelName);
        levelNameLabel.setStyle(
                "-fx-font-size: 32px;" +
                        "-fx-text-fill: YELLOW;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-color: BLACK;" +
                        "-fx-padding: 15;"
        );

        container.getChildren().addAll(levelUpLabel, levelNameLabel);
        getChildren().add(container);
    }
}