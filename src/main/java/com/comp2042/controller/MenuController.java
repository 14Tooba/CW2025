package com.comp2042.controller;

import com.comp2042.view.MainMenu;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controls main menu navigation.
 */
public class MenuController {

    private final Stage stage;
    private final MainMenu mainMenu;

    public MenuController(Stage stage) {
        this.stage = stage;
        this.mainMenu = new MainMenu();
        setupButtons();
    }

    private void setupButtons() {
        setupButtonsForMenu(mainMenu);
    }

    private void setupButtonsForMenu(MainMenu menu) {
        menu.getStartButton().setOnAction(e -> startGame());
        menu.getHighScoreButton().setOnAction(e -> showHighScores());
        menu.getQuitButton().setOnAction(e -> stage.close());
    }

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

    private void showHighScores() {
        System.out.println("High Scores - Coming Soon!");
    }

    public Scene getMenuScene() {
        // Create new MainMenu each time to avoid "already set as root" error
        MainMenu freshMenu = new MainMenu();
        setupButtonsForMenu(freshMenu);
        return new Scene(freshMenu, 300, 510);
    }
}