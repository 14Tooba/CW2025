package com.comp2042.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class SoundManagerTest {

    private SoundManager soundManager;

    @BeforeEach
    void setUp() {
        soundManager = new SoundManager();
    }

    @Test
    @DisplayName("Sound enabled by default")
    void testDefaultEnabled() {
        assertTrue(soundManager.isSoundEnabled());
    }

    @Test
    @DisplayName("Can disable sound")
    void testDisable() {
        soundManager.setSoundEnabled(false);
        assertFalse(soundManager.isSoundEnabled());
    }

    @Test
    @DisplayName("Can re-enable sound")
    void testReEnable() {
        soundManager.setSoundEnabled(false);
        soundManager.setSoundEnabled(true);
        assertTrue(soundManager.isSoundEnabled());
    }

    @Test
    @DisplayName("Play methods don't crash")
    void testPlayMethodsSafe() {
        assertDoesNotThrow(() -> soundManager.playLineClear());
        assertDoesNotThrow(() -> soundManager.playGameOver());
        assertDoesNotThrow(() -> soundManager.playDrop());
        assertDoesNotThrow(() -> soundManager.playRotate());
    }

    @Test
    @DisplayName("Play methods work when disabled")
    void testPlayWhenDisabled() {
        soundManager.setSoundEnabled(false);
        assertDoesNotThrow(() -> {
            soundManager.playLineClear();
            soundManager.playGameOver();
            soundManager.playDrop();
            soundManager.playRotate();
        });
    }

    @Test
    @DisplayName("Multiple toggle works correctly")
    void testMultipleToggle() {
        assertTrue(soundManager.isSoundEnabled());
        soundManager.setSoundEnabled(false);
        assertFalse(soundManager.isSoundEnabled());
        soundManager.setSoundEnabled(true);
        assertTrue(soundManager.isSoundEnabled());
        soundManager.setSoundEnabled(false);
        assertFalse(soundManager.isSoundEnabled());
    }
}