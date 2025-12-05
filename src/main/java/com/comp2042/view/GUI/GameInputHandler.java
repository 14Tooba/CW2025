package com.comp2042.view.GUI;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import com.comp2042.controller.InputEventListener;

/**
 * Handles keyboard input for gameplay controls.
 * Processes left, right, up (rotate), and G (ghost toggle) keys.
 * DOWN and SPACE are handled separately in GuiController.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class GameInputHandler implements EventHandler<KeyEvent> {

    private final InputEventListener eventListener;
    private final GuiController guiController;
    private boolean isPaused;
    private boolean isGameOver;

    /**
     * Creates a GameInputHandler.
     *
     * @param eventListener The InputEventListener (GameController)
     * @param guiController The GuiController for callback methods
     */
    public GameInputHandler(InputEventListener eventListener, GuiController guiController) {
        this.eventListener = eventListener;
        this.guiController = guiController;
        this.isPaused = false;
        this.isGameOver = false;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (!isPaused && !isGameOver) {
            handleGameplayInput(keyEvent);
        }

        handleGlobalInput(keyEvent);
    }

    /**
     * Handles gameplay input (movement, rotation, ghost).
     */
    private void handleGameplayInput(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        if (code == KeyCode.LEFT || code == KeyCode.A) {
            guiController.handleLeftMove();
            keyEvent.consume();
        }
        else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            guiController.handleRightMove();
            keyEvent.consume();
        }
        else if (code == KeyCode.UP || code == KeyCode.W) {
            guiController.handleRotate();
            keyEvent.consume();
        }
        else if (code == KeyCode.G) {
            guiController.handleGhostToggle();
            keyEvent.consume();
        }
    }

    /**
     * Handles global input (pause, new game).
     */
    private void handleGlobalInput(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        if (code == KeyCode.N) {
            guiController.handleNewGame();
            keyEvent.consume();
        }
        else if (code == KeyCode.P || code == KeyCode.ESCAPE) {
            guiController.handleTogglePause();
            keyEvent.consume();
        }
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
    }
}