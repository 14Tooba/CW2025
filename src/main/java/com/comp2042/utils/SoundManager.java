package com.comp2042.utils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Simple sound manager using Java's built-in audio support.
 */
public class SoundManager {

    private Clip backgroundMusicClip;
    private boolean musicEnabled = true;
    private boolean soundEnabled = true;
    private float volume = 0.5f; // 50% volume

    /**
     * Constructor
     */
    public SoundManager() {
        loadBackgroundMusic();
    }

    /**
     * Loads background music using javax.sound
     */
    private void loadBackgroundMusic() {
        try {
            URL musicUrl = getClass().getResource("/background_music.wav"); // Note: Use WAV format
            if (musicUrl != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicUrl);
                backgroundMusicClip = AudioSystem.getClip();
                backgroundMusicClip.open(audioIn);

                // Set volume
                FloatControl volumeControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                volumeControl.setValue(dB);

                // Set to loop continuously
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);

                System.out.println("Background music loaded successfully!");
            } else {
                System.err.println("Background music file not found!");
            }
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
        }
    }

    /**
     * Starts playing background music
     */
    public void startBackgroundMusic() {
        if (backgroundMusicClip != null && musicEnabled) {
            backgroundMusicClip.start();
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Background music started");
        }
    }

    /**
     * Stops background music
     */
    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.stop();
            System.out.println("Background music stopped");
        }
    }

    /**
     * Pauses background music
     */
    public void pauseBackgroundMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.stop();
            System.out.println("Background music paused");
        }
    }

    /**
     * Resumes background music
     */
    public void resumeBackgroundMusic() {
        if (backgroundMusicClip != null && musicEnabled) {
            backgroundMusicClip.start();
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Background music resumed");
        }
    }

    /**
     * Toggles music on/off
     */
    public void toggleBackgroundMusic() {
        musicEnabled = !musicEnabled;
        if (musicEnabled) {
            startBackgroundMusic();
        } else {
            pauseBackgroundMusic();
        }
    }

    // Simple sound effect methods
    public void playMove() {
        // Implementation for move sound
    }

    public void playRotate() {
        // Implementation for rotate sound
    }

    public void playDrop() {
        // Implementation for drop sound
    }

    public void playLineClear() {
        // Implementation for line clear sound
    }

    public void playGameOver() {
        // Implementation for game over sound
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
    }
}