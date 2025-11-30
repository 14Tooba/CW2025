package com.comp2042.model.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for level progression system.
 */
@DisplayName("Level Progression Tests")
public class LevelProgressionTest {

    @Test
    @DisplayName("Test complete level cycle")
    void testCompleteLevelCycle() {
        GameLevel level = GameLevel.CLASSIC;

        // Test progression
        assertEquals(GameLevel.CLASSIC, level);
        level = level.getNextLevel();
        assertEquals(GameLevel.LAVA_SURVIVAL, level);
        level = level.getNextLevel();
        assertEquals(GameLevel.TARGET_CHALLENGE, level);
        level = level.getNextLevel();
        assertEquals(GameLevel.CLASSIC, level);
    }

    @Test
    @DisplayName("Test level display names")
    void testLevelDisplayNames() {
        assertEquals("CLASSIC MODE", GameLevel.CLASSIC.getDisplayName());
        assertEquals("LAVA MODE", GameLevel.LAVA_SURVIVAL.getDisplayName());
        assertEquals("TARGET CHALLENGE", GameLevel.TARGET_CHALLENGE.getDisplayName());
    }

    @Test
    @DisplayName("Test lines required to advance")
    void testLinesRequired() {
        assertEquals(1, GameLevel.CLASSIC.getLinesRequiredToAdvance());
        assertEquals(2, GameLevel.LAVA_SURVIVAL.getLinesRequiredToAdvance());
        assertEquals(0, GameLevel.TARGET_CHALLENGE.getLinesRequiredToAdvance());
    }
}