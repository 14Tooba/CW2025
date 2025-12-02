package com.comp2042.model.scoring;

import java.io.*;
import java.util.*;

/**
 * Manages high scores persistence and retrieval.
 */
public class HighScoreManager {
    private static final String SCORE_FILE = "highscores.txt";
    private static final int MAX_SCORES = 10;
    private List<Integer> scores;

    /**
     * Creates high score manager and loads existing scores.
     */
    public HighScoreManager() {
        scores = new ArrayList<>();
        loadScores();
    }

    /**
     * Adds a new score and saves to file.
     * @param score Score to add
     */
    public void addScore(int score) {
        scores.add(score);
        scores.sort(Collections.reverseOrder());
        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }
        saveScores();
    }

    /**
     * Gets all high scores sorted.
     * @return List of scores
     */
    public List<Integer> getHighScores() {
        return new ArrayList<>(scores);
    }

    /**
     * Gets the highest score.
     * @return Highest score or 0
     */
    public int getTopScore() {
        return scores.isEmpty() ? 0 : scores.get(0);
    }

    /**
     * Loads scores from file.
     */
    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Integer.parseInt(line));
            }
            scores.sort(Collections.reverseOrder());
        } catch (IOException e) {
            // File doesn't exist yet, that's okay
        }
    }

    /**
     * Saves scores to file.
     */
    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_FILE))) {
            for (int score : scores) {
                writer.println(score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}