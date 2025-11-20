package com.comp2042.view;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {

    @BeforeAll
    static void initJFX() {
        Platform.startup(() -> {});
    }

    @Test
    @DisplayName("MainMenu has start button")
    void testHasStartButton() {
        Platform.runLater(() -> {
            MainMenu menu = new MainMenu();
            assertNotNull(menu.getStartButton());
            assertEquals("START GAME", menu.getStartButton().getText());
        });
    }

    @Test
    @DisplayName("MainMenu has high score button")
    void testHasHighScoreButton() {
        Platform.runLater(() -> {
            MainMenu menu = new MainMenu();
            assertNotNull(menu.getHighScoreButton());
            assertEquals("HIGH SCORES", menu.getHighScoreButton().getText());
        });
    }

    @Test
    @DisplayName("MainMenu has quit button")
    void testHasQuitButton() {
        Platform.runLater(() -> {
            MainMenu menu = new MainMenu();
            assertNotNull(menu.getQuitButton());
            assertEquals("QUIT", menu.getQuitButton().getText());
        });
    }

    @Test
    @DisplayName("All buttons exist and are not null")
    void testAllButtonsExist() {
        Platform.runLater(() -> {
            MainMenu menu = new MainMenu();
            assertNotNull(menu.getStartButton());
            assertNotNull(menu.getHighScoreButton());
            assertNotNull(menu.getQuitButton());
        });
    }

    @Test
    @DisplayName("Can create multiple MainMenu instances")
    void testMultipleInstances() {
        Platform.runLater(() -> {
            MainMenu menu1 = new MainMenu();
            MainMenu menu2 = new MainMenu();
            assertNotNull(menu1);
            assertNotNull(menu2);
            assertNotSame(menu1, menu2);
        });
    }
}