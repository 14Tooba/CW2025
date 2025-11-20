package com.comp2042.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Displays "LEVEL UP!" notification when player advances levels.
 *
 */
public class LevelUpNotification extends StackPane {

    public LevelUpNotification(String levelName) {
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.9);");

        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);

        // "LEVEL UP!" text
        Label levelUpLabel = new Label("LEVEL UP!");
        levelUpLabel.setStyle(
                "-fx-font-size: 48px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.8), 20, 0, 0, 0);"
        );

        // Level name
        Label levelNameLabel = new Label(levelName);
        levelNameLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-text-fill: #FF6B6B;" +
                        "-fx-font-weight: bold;"
        );

        container.getChildren().addAll(levelUpLabel, levelNameLabel);
        getChildren().add(container);

        setVisible(false);
    }
}