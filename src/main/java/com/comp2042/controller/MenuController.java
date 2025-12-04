package com.comp2042.controller;

import com.comp2042.view.MainMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controls navigation and interactions for the main menu system.
 * Manages scene transitions between menu and game, and handles button events.
 * Follows the Controller pattern from MVC architecture.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class MenuController {

    private final Stage stage;
    private final MainMenu mainMenu;

    /**
     * Constructs a MenuController with the specified stage.
     * Initializes the main menu and sets up button event handlers.
     *
     * @param stage The primary stage for scene management
     */
    public MenuController(Stage stage) {
        this.stage = stage;
        this.mainMenu = new MainMenu();
        setupButtons();
    }

    /**
     * Initializes all button event handlers for the main menu.
     * Configures start, high score, and quit button actions.
     */
    private void setupButtons() {
        setupButtonsForMenu(mainMenu);
    }

    private void setupButtonsForMenu(MainMenu menu) {
        menu.getStartButton().setOnAction(e -> startGame());
        menu.getHighScoreButton().setOnAction(e -> showHighScores());
        menu.getQuitButton().setOnAction(e -> stage.close());
    }

/**
 * Initiates a new game by loading the game scene and controllers.
 * Loads FXML layout, creates GuiController and GameController instances,
 * and transitions from menu to game scene.
 *
 * @throws RuntimeException if FXML file cannot be loaded
 */

    private void startGame() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getClassLoader().getResource("gameLayout.fxml")
            );
            javafx.scene.Parent root = loader.load();
            com.comp2042.view.GuiController guiController = loader.getController();

            Scene gameScene = new Scene(root, 300, 510);
            stage.setScene(gameScene);

            guiController.setStageAndMenu(stage, this);
            new GameController(guiController, stage, this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Displays the high scores screen.
     * Currently shows a placeholder message for future implementation.
     */

    private void showHighScores() {
        System.out.println("High Scores - Coming Soon!");
    }

    /**
     * Creates and returns a fresh menu scene.
     * Generates a new MainMenu instance to avoid "already set as root" errors.
     *
     * @return Scene containing the main menu layout
     */
    public Scene getMenuScene() {
        // Create new MainMenu each time to avoid "already set as root" error
        MainMenu freshMenu = new MainMenu();
        setupButtonsForMenu(freshMenu);
        return new Scene(freshMenu, 300, 510);
    }
}