package com.comp2042.utils;

import java.awt.Toolkit;

/**
 * Manages game sound effects using system beep.
 * Simple implementation without external audio files.
 */
public class SoundManager {

    private boolean soundEnabled = true;
    private final Toolkit toolkit;

    public SoundManager() {
        toolkit = Toolkit.getDefaultToolkit();
    }

    /**
     * Plays line clear sound effect.
     */
    public void playLineClear() {
        if (soundEnabled) {
            toolkit.beep();
        }
    }

    /**
     * Plays game over sound effect.
     */
    public void playGameOver() {
        if (soundEnabled) {
            // Double beep for emphasis
            toolkit.beep();
            try {
                Thread.sleep(100);
                toolkit.beep();
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }

    /**
     * Plays brick drop sound effect.
     */
    public void playDrop() {
        if (soundEnabled) {
            toolkit.beep();
        }
    }

    /**
     * Plays brick rotate sound effect.
     */
    public void playRotate() {
        if (soundEnabled) {
            toolkit.beep();
        }
    }

    /**
     * Enables or disables all sounds.
     */
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }

    /**
     * Checks if sound is currently enabled.
     */
    public boolean isSoundEnabled() {
        return soundEnabled;
    }
}