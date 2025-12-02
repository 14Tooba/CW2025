package com.comp2042.view;

import com.comp2042.model.scoring.HighScoreManager;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.util.List;

/**
 * Displays high scores screen.
 */
public class HighScoreScreen extends VBox {
    private HighScoreManager scoreManager;
    private Button backButton;

    /**
     * Creates high score display.
     */
    public HighScoreScreen() {
        scoreManager = new HighScoreManager();

        setAlignment(Pos.CENTER);
        setSpacing(15);
        setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30;");

        // Title
        Label title = new Label("HIGH SCORES");
        title.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 36px; -fx-font-weight: bold;");

        // Get scores
        List<Integer> scores = scoreManager.getHighScores();

        VBox scoreList = new VBox(10);
        scoreList.setAlignment(Pos.CENTER);

        if (scores.isEmpty()) {
            Label noScores = new Label("No scores yet!");
            noScores.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
            scoreList.getChildren().add(noScores);
        } else {
            // Show top score specially
            Label topScore = new Label("🏆 TOP: " + scores.get(0));
            topScore.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 24px; -fx-font-weight: bold;");
            scoreList.getChildren().add(topScore);

            // Show other scores
            for (int i = 1; i < scores.size(); i++) {
                Label scoreLabel = new Label((i + 1) + ". " + scores.get(i));
                scoreLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
                scoreList.getChildren().add(scoreLabel);
            }
        }

        // Back button
        backButton = new Button("BACK TO MENU");
        backButton.setStyle("-fx-background-color: #00FFFF; -fx-text-fill: black; " +
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;");

        getChildren().addAll(title, scoreList, backButton);
    }

    /**
     * Gets the back button.
     * @return Back button
     */
    public Button getBackButton() {
        return backButton;
    }
}