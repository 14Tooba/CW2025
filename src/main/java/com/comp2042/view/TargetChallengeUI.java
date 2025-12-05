package com.comp2042.view;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.comp2042.controller.GameController;
import com.comp2042.model.game.TargetChallengeManager;

/**
 * Manages the UI display for Target Challenge mode.
 * Handles mission information, block counter, and countdown timer display.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class TargetChallengeUI {

    private final VBox targetChallengeContainer;
    private final Label missionLabel;
    private final Label targetBlocksLabel;
    private final Label timerLabel;
    private boolean isActive = false;
    private AnimationTimer timer;
    private GameController gameController;

    /**
     * Creates a TargetChallengeUI with FXML-injected components.
     *
     * @param container The container VBox
     * @param missionLabel Label for mission name
     * @param blocksLabel Label for remaining blocks
     * @param timerLabel Label for countdown timer
     */
    public TargetChallengeUI(VBox container, Label missionLabel,
                             Label blocksLabel, Label timerLabel) {
        this.targetChallengeContainer = container;
        this.missionLabel = missionLabel;
        this.targetBlocksLabel = blocksLabel;
        this.timerLabel = timerLabel;

        hide(); // Start hidden
    }

    /**
     * Sets the game controller reference for timer updates.
     *
     * @param controller The GameController instance
     */
    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    /**
     * Updates the Target Challenge display with current information.
     * Shows mission name, remaining blocks, and formatted time.
     *
     * @param remainingBlocks Number of blocks still to clear
     * @param formattedTime Time remaining in MM:SS format
     * @param mission Current mission type
     */
    public void updateDisplay(int remainingBlocks, String formattedTime,
                              TargetChallengeManager.MissionType mission) {
        isActive = true;

        if (targetChallengeContainer != null) {
            targetChallengeContainer.setVisible(true);
            targetChallengeContainer.setManaged(true);
        }

        if (targetBlocksLabel != null) {
            targetBlocksLabel.setText("Blocks: " + remainingBlocks);
        }
        if (timerLabel != null) {
            timerLabel.setText("Time: " + formattedTime);
        }
        if (missionLabel != null && mission != null) {
            missionLabel.setText(mission.getName());
        }

        if (timer == null) {
            startTimer();
        }
    }

    /**
     * Hides the Target Challenge UI.
     */
    public void hide() {
        isActive = false;
        if (targetChallengeContainer != null) {
            targetChallengeContainer.setVisible(false);
            targetChallengeContainer.setManaged(false);
        }
        stopTimer();
    }

    /**
     * Starts the animation timer for real-time countdown updates.
     */
    private void startTimer() {
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000) {
                    if (isActive && gameController != null) {
                        if (gameController.getBoard().getTargetChallengeManager().isActive()) {
                            String time = gameController.getBoard()
                                    .getTargetChallengeManager().getFormattedTime();
                            if (timerLabel != null) {
                                timerLabel.setText("Time: " + time);
                            }
                        }
                    }
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    /**
     * Stops the animation timer.
     */
    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    /**
     * Checks if Target Challenge UI is currently active.
     *
     * @return true if active
     */
    public boolean isActive() {
        return isActive;
    }
}