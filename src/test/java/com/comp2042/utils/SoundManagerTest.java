package com.comp2042.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SoundManager functionality.
 * Tests the simplified version without JavaFX Media dependencies.
 */
@DisplayName("SoundManager Tests")
public class SoundManagerTest {

    private SoundManager soundManager;

    @BeforeEach
    void setUp() {
        // Create a new SoundManager for each test
        soundManager = new SoundManager();
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test
        if (soundManager != null) {
            soundManager.stopBackgroundMusic();
        }
    }

    @Test
    @DisplayName("Test SoundManager initialization")
    void testInitialization() {
        assertNotNull(soundManager, "SoundManager should be initialized");
        assertTrue(soundManager.isMusicEnabled(), "Music should be enabled by default");
    }

    @Test
    @DisplayName("Test music enabled/disabled state")
    void testMusicEnabledState() {
        // Initially enabled
        assertTrue(soundManager.isMusicEnabled());

        // Disable music
        soundManager.setMusicEnabled(false);
        assertFalse(soundManager.isMusicEnabled());

        // Re-enable music
        soundManager.setMusicEnabled(true);
        assertTrue(soundManager.isMusicEnabled());
    }

    @Test
    @DisplayName("Test toggle background music")
    void testToggleBackgroundMusic() {
        // Initially enabled
        assertTrue(soundManager.isMusicEnabled());

        // Toggle off
        soundManager.toggleBackgroundMusic();
        assertFalse(soundManager.isMusicEnabled());

        // Toggle on
        soundManager.toggleBackgroundMusic();
        assertTrue(soundManager.isMusicEnabled());
    }

    @Test
    @DisplayName("Test start background music - no exceptions")
    void testStartBackgroundMusic() {
        assertDoesNotThrow(() -> {
            soundManager.startBackgroundMusic();
        }, "Starting background music should not throw exceptions");
    }

    @Test
    @DisplayName("Test stop background music - no exceptions")
    void testStopBackgroundMusic() {
        assertDoesNotThrow(() -> {
            soundManager.startBackgroundMusic();
            soundManager.stopBackgroundMusic();
        }, "Stopping background music should not throw exceptions");
    }

    @Test
    @DisplayName("Test pause background music - no exceptions")
    void testPauseBackgroundMusic() {
        assertDoesNotThrow(() -> {
            soundManager.startBackgroundMusic();
            soundManager.pauseBackgroundMusic();
        }, "Pausing background music should not throw exceptions");
    }

    @Test
    @DisplayName("Test resume background music - no exceptions")
    void testResumeBackgroundMusic() {
        assertDoesNotThrow(() -> {
            soundManager.startBackgroundMusic();
            soundManager.pauseBackgroundMusic();
            soundManager.resumeBackgroundMusic();
        }, "Resuming background music should not throw exceptions");
    }

    @Test
    @DisplayName("Test sound effect methods - no exceptions")
    void testSoundEffectMethods() {
        assertDoesNotThrow(() -> {
            soundManager.playMove();
            soundManager.playRotate();
            soundManager.playDrop();
            soundManager.playLineClear();
            soundManager.playGameOver();
        }, "Sound effect methods should not throw exceptions");
    }

    @Test
    @DisplayName("Test music state persistence through pause/resume")
    void testMusicStatePersistence() {
        // Start with music enabled
        soundManager.setMusicEnabled(true);
        soundManager.startBackgroundMusic();

        // Pause and check state
        soundManager.pauseBackgroundMusic();
        assertTrue(soundManager.isMusicEnabled(), "Music should still be enabled after pause");

        // Resume and check state
        soundManager.resumeBackgroundMusic();
        assertTrue(soundManager.isMusicEnabled(), "Music should still be enabled after resume");
    }

    @Test
    @DisplayName("Test stop then start sequence")
    void testStopThenStartSequence() {
        assertDoesNotThrow(() -> {
            // Start music
            soundManager.startBackgroundMusic();

            // Stop music
            soundManager.stopBackgroundMusic();

            // Start again
            soundManager.startBackgroundMusic();
        }, "Stop then start sequence should work without exceptions");
    }

    @Test
    @DisplayName("Test multiple pause/resume cycles")
    void testMultiplePauseResumeCycles() {
        assertDoesNotThrow(() -> {
            soundManager.startBackgroundMusic();

            // Multiple cycles
            for (int i = 0; i < 3; i++) {
                soundManager.pauseBackgroundMusic();
                soundManager.resumeBackgroundMusic();
            }
        }, "Multiple pause/resume cycles should work without exceptions");
    }

    @Test
    @DisplayName("Test disabled music prevents playback")
    void testDisabledMusicPreventsPlayback() {
        // Disable music
        soundManager.setMusicEnabled(false);

        // Try to start (should handle gracefully)
        assertDoesNotThrow(() -> {
            soundManager.startBackgroundMusic();
            soundManager.resumeBackgroundMusic();
        }, "Starting/resuming with disabled music should not throw exceptions");

        // Music should remain disabled
        assertFalse(soundManager.isMusicEnabled());
    }

    @Test
    @DisplayName("Test null safety - multiple stops")
    void testNullSafetyMultipleStops() {
        assertDoesNotThrow(() -> {
            // Stop without starting
            soundManager.stopBackgroundMusic();

            // Stop again
            soundManager.stopBackgroundMusic();
        }, "Multiple stops should be handled safely");
    }

    @Test
    @DisplayName("Test null safety - pause without start")
    void testNullSafetyPauseWithoutStart() {
        assertDoesNotThrow(() -> {
            // Pause without starting
            soundManager.pauseBackgroundMusic();
        }, "Pause without start should be handled safely");
    }
}