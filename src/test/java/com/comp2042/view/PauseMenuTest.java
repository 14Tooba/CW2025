package com.comp2042.view;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PauseMenuTest {

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {});
    }

    @Test
    @DisplayName("PauseMenu initially hidden")
    void testInitiallyHidden() {
        Platform.runLater(() -> {
            PauseMenu menu = new PauseMenu();
            assertFalse(menu.isVisible());
        });
    }

    @Test
    @DisplayName("Has resume button with correct text")
    void testResumeButton() {
        Platform.runLater(() -> {
            PauseMenu menu = new PauseMenu();
            assertNotNull(menu.getResumeButton());
            assertEquals("RESUME", menu.getResumeButton().getText());
        });
    }

    @Test
    @DisplayName("Has quit button with correct text")
    void testQuitButton() {
        Platform.runLater(() -> {
            PauseMenu menu = new PauseMenu();
            assertNotNull(menu.getQuitButton());
            assertEquals("QUIT", menu.getQuitButton().getText());
        });
    }

    @Test
    @DisplayName("Both buttons are not null")
    void testButtonsExist() {
        Platform.runLater(() -> {
            PauseMenu menu = new PauseMenu();
            assertNotNull(menu.getResumeButton());
            assertNotNull(menu.getQuitButton());
        });
    }

    @Test
    @DisplayName("Restart button provides backward compatibility")
    void testBackwardCompatibility() {
        Platform.runLater(() -> {
            PauseMenu menu = new PauseMenu();
            assertSame(menu.getQuitButton(), menu.getRestartButton());
        });
    }
}