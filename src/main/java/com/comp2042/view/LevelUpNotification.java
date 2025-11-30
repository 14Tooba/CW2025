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
        setPrefSize(500, 600);
        setMinSize(400, 500);
        setMaxSize(600, 700);

        // Check if it's lava mode or target challenge for special styling
        boolean isLavaMode = levelName.toUpperCase().contains("LAVA");
        boolean isTargetChallenge = levelName.toUpperCase().contains("TARGET");

        if (isLavaMode) {
            createLavaNotification();
        } else if (isTargetChallenge) {
            createTargetChallengeNotification();
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
                "-fx-font-size: 56px;" +
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
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FF4500;" +
                        "-fx-padding: 3 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        // "LAVA SURVIVAL" - Smaller with subtle glow
        Label lavaModeLabel = new Label("🔥 LAVA SURVIVAL 🔥");
        lavaModeLabel.setStyle(
                "-fx-font-size: 42px;" +
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
                "-fx-font-size: 18px;" +
                        "-fx-text-fill: #FFA500;" +
                        "-fx-font-style: italic;" +
                        "-fx-padding: 3 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        // Objective
        Label objectiveLabel = new Label("Clear 2 lines to survive");
        objectiveLabel.setStyle(
                "-fx-font-size: 17px;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        // Warning at bottom
        Label bottomWarning = new Label("Don't let lava touch your blocks!");
        bottomWarning.setStyle(
                "-fx-font-size: 17px;" +
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
     * Creates notification for Target Challenge mode.
     */
    private void createTargetChallengeNotification() {
        // Purple gradient background
        setStyle("-fx-background-color: linear-gradient(to bottom, #2D0A4B, #4A148C, #2D0A4B);");

        VBox container = new VBox(15);
        container.setAlignment(Pos.CENTER);

        // "LEVEL UP!" with target icon
        Label levelUpLabel = new Label("🎯 LEVEL UP 🎯");
        levelUpLabel.setStyle(
                "-fx-font-size: 56px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #00FFFF;" +
                        "-fx-font-smoothing-type: lcd;"
        );
        DropShadow cyanGlow = new DropShadow();
        cyanGlow.setColor(Color.CYAN);
        cyanGlow.setRadius(15);
        cyanGlow.setSpread(0.4);
        levelUpLabel.setEffect(cyanGlow);

        // Challenge label
        Label challengeLabel = new Label("⭐ MISSION MODE ⭐");
        challengeLabel.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FFD700;" +
                        "-fx-padding: 3 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        // "TARGET CHALLENGE" title
        Label targetModeLabel = new Label("TARGET CHALLENGE");
        targetModeLabel.setStyle(
                "-fx-font-size: 42px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FF69B4;" +
                        "-fx-font-smoothing-type: lcd;"
        );
        DropShadow pinkGlow = new DropShadow();
        pinkGlow.setColor(Color.rgb(255, 105, 180));
        pinkGlow.setRadius(12);
        pinkGlow.setSpread(0.3);
        targetModeLabel.setEffect(pinkGlow);

        // Mission description
        Label missionLabel = new Label("Complete special missions!");
        missionLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-style: italic;" +
                        "-fx-padding: 3 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        // Objectives
        Label objective1 = new Label("• Clear pre-filled patterns");
        objective1.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: #00FF00;" +
                        "-fx-padding: 5 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        Label objective2 = new Label("• Time limit: 3 minutes");
        objective2.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: #FFA500;" +
                        "-fx-padding: 2 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        // Warning
        Label warningLabel = new Label("⏰ Beat the clock!");
        warningLabel.setStyle(
                "-fx-font-size: 17px;" +
                        "-fx-text-fill: #FF4444;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 0 0 0;" +
                        "-fx-font-smoothing-type: lcd;"
        );

        container.getChildren().addAll(
                levelUpLabel,
                challengeLabel,
                targetModeLabel,
                missionLabel,
                objective1,
                objective2,
                warningLabel
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