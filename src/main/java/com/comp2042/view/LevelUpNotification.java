package com.comp2042.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * Epic level up notification with special styling for lava mode.
 *
 */
public class LevelUpNotification extends StackPane {

    public LevelUpNotification(String levelName) {
        setPrefSize(300, 510);
        setMinSize(300, 510);
        setMaxSize(300, 510);

        // Check if it's lava mode for special styling
        boolean isLavaMode = levelName.toUpperCase().contains("LAVA");

        if (isLavaMode) {
            createLavaNotification();
        } else {
            createClassicNotification();
        }
    }

    /**
     * Creates epic lava-themed notification with subtle fire effects.
     */
    private void createLavaNotification() {
        // Dramatic dark background
        setStyle("-fx-background-color: linear-gradient(to bottom, #1a0000, #330000, #1a0000);");

        VBox container = new VBox(18);
        container.setAlignment(Pos.CENTER);

        // "LEVEL UP!" - Gold with subtle glow
        Label levelUpLabel = new Label("⚠ LEVEL UP ⚠");
        levelUpLabel.setStyle(
                "-fx-font-size: 42px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-font-smoothing-type: lcd;"
        );
        DropShadow goldGlow = new DropShadow();
        goldGlow.setColor(Color.GOLD);
        goldGlow.setRadius(15);
        goldGlow.setSpread(0.4);
        levelUpLabel.setEffect(goldGlow);

        // Warning label
        Label warningLabel = new Label("⚠ DANGER AHEAD ⚠");
        warningLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FF4500;" +
                        "-fx-padding: 3 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        // "LAVA SURVIVAL" - Smaller with subtle glow
        Label lavaModeLabel = new Label("🔥 LAVA SURVIVAL 🔥");
        lavaModeLabel.setStyle(
                "-fx-font-size: 32px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FF4500;" +
                        "-fx-font-smoothing-type: lcd;"
        );
        DropShadow fireGlow = new DropShadow();
        fireGlow.setColor(Color.rgb(255, 69, 0));
        fireGlow.setRadius(12);
        fireGlow.setSpread(0.3);
        lavaModeLabel.setEffect(fireGlow);

        // Challenge description
        Label challengeLabel = new Label("Lava descends from above!");
        challengeLabel.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-text-fill: #FFA500;" +
                        "-fx-font-style: italic;" +
                        "-fx-padding: 3 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        // Objective
        Label objectiveLabel = new Label("Clear 2 lines to survive");
        objectiveLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        // Warning at bottom
        Label bottomWarning = new Label("Don't let lava touch your blocks!");
        bottomWarning.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: #FF6B6B;" +
                        "-fx-padding: 12 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        container.getChildren().addAll(
                levelUpLabel,
                warningLabel,
                lavaModeLabel,
                challengeLabel,
                objectiveLabel,
                bottomWarning
        );

        getChildren().add(container);
    }

    /**
     * Creates simple notification for classic mode.
     */
    private void createClassicNotification() {
        // Clean blue background
        setStyle("-fx-background-color: #0066CC;");

        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);

        Label levelUpLabel = new Label("LEVEL UP!");
        levelUpLabel.setStyle(
                "-fx-font-size: 48px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: WHITE;"
        );

        Label modeLabel = new Label("CLASSIC MODE");
        modeLabel.setStyle(
                "-fx-font-size: 36px;" +
                        "-fx-text-fill: YELLOW;" +
                        "-fx-font-weight: bold;"
        );

        Label descLabel = new Label("Back to normal gameplay");
        descLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: WHITE;" +
                        "-fx-padding: 10 0 0 0;"
        );

        container.getChildren().addAll(levelUpLabel, modeLabel, descLabel);
        getChildren().add(container);
    }
}