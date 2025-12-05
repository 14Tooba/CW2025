package com.comp2042.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

/**
 * In-game pause menu overlay with resume and quit options.
 * Displays as a semi-transparent centered panel during gameplay pause.
 * Provides visual feedback through styled buttons.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class PauseMenu extends VBox {

    private Button resumeButton;
    private Button quitButton;

    /**
     * Creates styled pause menu with buttons.
     */
    public PauseMenu() {
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setPrefSize(250, 350);

        setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); " +
                "-fx-border-color: #00FFFF; " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 15; " +
                "-fx-background-radius: 15;");

        // Title
        Label pauseLabel = new Label("GAME PAUSED");
        pauseLabel.setStyle("-fx-text-fill: #FFD700; " +
                "-fx-font-size: 28px; " +
                "-fx-font-weight: bold;");

        // Resume button
        resumeButton = new Button("RESUME");
        styleButton(resumeButton, "#00FF00");

        // Quit button
        quitButton = new Button("QUIT TO MENU");
        styleButton(quitButton, "#FF4444");

        // Instructions
        Label instructionLabel = new Label("Press P to Resume");
        instructionLabel.setStyle("-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 14px;");

        getChildren().addAll(pauseLabel, resumeButton, quitButton, instructionLabel);
        setVisible(false);
    }

    /**
     * Applies consistent styling to menu buttons including hover effects.
     *
     * @param button The button to style
     * @param color Base color for the button
     */
    private void styleButton(Button button, String color) {
        button.setPrefWidth(150);
        String baseStyle = "-fx-background-color: " + color + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand;";
        button.setStyle(baseStyle);

        button.setOnMouseEntered(e ->
                button.setStyle(baseStyle + "-fx-opacity: 0.8;"));
        button.setOnMouseExited(e ->
                button.setStyle(baseStyle));
    }

    /**
     * Gets the resume button.
     * @return Resume button
     */
    public Button getResumeButton() {
        return resumeButton;
    }

    /**
     * Gets the quit button.
     * @return Quit button
     */
    public Button getQuitButton() {
        return quitButton;
    }
}