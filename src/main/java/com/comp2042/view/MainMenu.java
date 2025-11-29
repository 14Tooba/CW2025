package com.comp2042.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;


/**
 * Main menu screen for Tetris game.
 */
public class MainMenu extends StackPane {

    private final Button startButton;
    private final Button highScoreButton;
    private final Button quitButton;

    public MainMenu() {
        // Background
        setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e, #0f3460);");

        // Container
        VBox menuContainer = new VBox(30);
        menuContainer.setAlignment(Pos.CENTER);

        // Title
        Label titleLabel = new Label("TETRIS");
        titleLabel.setStyle(
                "-fx-font-size: 72px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.8), 20, 0, 0, 0);"
        );

        Label tipLabel = new Label("💡 Maximize window for better game experience");
        tipLabel.setStyle("-fx-text-fill: #FF69B4; " +  // Pink color
                "-fx-font-size: 14px; " +
                "-fx-font-style: italic; " +
                "-fx-padding: 0 0 20 0;");  // Space below
        tipLabel.setAlignment(Pos.CENTER);

        // Subtitle
        Label subtitleLabel = new Label("Enhanced Edition");
        subtitleLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-text-fill: #B0B0B0;" +
                        "-fx-font-style: italic;"
        );

        // Buttons
        startButton = createButton("START GAME", "#4CAF50", "#45a049");
        highScoreButton = createButton("HIGH SCORES", "#2196F3", "#0b7dda");
        quitButton = createButton("QUIT", "#f44336", "#da190b");


        // Instructions
        Label instructionsLabel = new Label("Controls: ← → ↑ ↓ or WASD • P: Pause • G: Ghost");
        instructionsLabel.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: #808080;" +
                        "-fx-padding: 30 0 0 0;"
        );

        // Assemble
        VBox titleBox = new VBox(10, titleLabel, subtitleLabel);
        titleBox.setAlignment(Pos.CENTER);

        VBox buttonBox = new VBox(15, startButton, highScoreButton, quitButton);
        buttonBox.setAlignment(Pos.CENTER);

        menuContainer.getChildren().addAll(titleBox, tipLabel, buttonBox, instructionsLabel);
        getChildren().add(menuContainer);
    }

    private Button createButton(String text, String color, String hoverColor) {
        Button button = new Button(text);
        button.setPrefWidth(280);
        button.setPrefHeight(55);
        button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 15;" +
                        "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: " + hoverColor + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 15;" +
                        "-fx-cursor: hand;" +
                        "-fx-scale-x: 1.05;" +
                        "-fx-scale-y: 1.05;"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 15;" +
                        "-fx-cursor: hand;"
        ));

        return button;
    }

    public Button getStartButton() { return startButton; }
    public Button getHighScoreButton() { return highScoreButton; }
    public Button getQuitButton() { return quitButton; }
}