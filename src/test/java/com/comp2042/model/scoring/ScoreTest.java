package com.comp2042.model.scoring;

import com.comp2042.model.scoring.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score();
    }

    @Test
    @DisplayName("Initial score is zero")
    void testInitialScore() {
        assertEquals(0, score.scoreProperty().get());
    }

    @Test
    @DisplayName("Adding score works correctly")
    void testAddScore() {
        score.add(10);
        assertEquals(10, score.scoreProperty().get());

        score.add(25);
        assertEquals(35, score.scoreProperty().get());
    }

    @Test
    @DisplayName("Reset clears score to zero")
    void testReset() {
        score.add(500);
        score.reset();
        assertEquals(0, score.scoreProperty().get());
    }

    @Test
    @DisplayName("Negative values work (penalties)")
    void testNegativeScore() {
        score.add(100);
        score.add(-20);
        assertEquals(80, score.scoreProperty().get());
    }
}