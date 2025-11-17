package com.comp2042.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

/**
 * Pause menu overlay for the game.
 * Clean, centered design with visible text buttons.
 */
public class PauseMenu extends StackPane {

    private final Button resumeButton;
    private final Button quitButton;

    public PauseMenu() {
        // Semi-transparent dark background
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.85);");

        // Main menu container
        VBox menuBox = new VBox(25);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setMaxWidth(300);
        menuBox.setStyle(
                "-fx-background-color: #2c2c2c;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 50 40 50 40;" +
                        "-fx-border-color: #5a9fd4;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 25, 0, 0, 0);"
        );

        // Title
        Label titleLabel = new Label("PAUSED");
        titleLabel.setStyle(
                "-fx-font-size: 42px;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Arial';"
        );

        // Resume Button
        resumeButton = new Button("RESUME");
        styleButton(resumeButton, "#4CAF50", "#45a049");

        // Quit Button
        quitButton = new Button("QUIT");
        styleButton(quitButton, "#f44336", "#da190b");

        // Instruction
        Label hintLabel = new Label("Press P or ESC to resume");
        hintLabel.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #b0b0b0;" +
                        "-fx-font-family: 'Arial';"
        );

        // Add everything to menu
        menuBox.getChildren().addAll(titleLabel, resumeButton, quitButton, hintLabel);

        // Center the menu box
        getChildren().add(menuBox);
        setAlignment(Pos.TOP_LEFT); //ADJUSTED so that it fits the screen
        setVisible(false);
    }

    /**
     * Styles a button with colors and hover effects.
     */
    private void styleButton(Button button, String normalColor, String hoverColor) {
        button.setPrefWidth(220);
        button.setPrefHeight(50);
        button.setStyle(
                "-fx-background-color: " + normalColor + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Arial';" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"
        );

        // Hover effect - change color
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: " + hoverColor + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 18px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 10, 0, 0, 0);"
            );
        });

        // Mouse exit - back to normal
        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: " + normalColor + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 18px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-family: 'Arial';" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;"
            );
        });
    }

    public Button getResumeButton() {
        return resumeButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    // Backward compatibility
    public Button getRestartButton() {
        return quitButton;
    }
}