package com.comp2042.view;

import com.comp2042.EventSource;
import com.comp2042.EventType;
import com.comp2042.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Game timer for automatic brick falling at regular intervals.
 * Manages timeline for gravity-based piece descent with pause/resume support.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class GameTimer {

    private final Timeline timeline;
    private final MoveHandler moveHandler;

    /**
     * Interface for handling timer-triggered moves.
     */
    public interface MoveHandler {
        void onTimerTick();
    }

    /**
     * Creates a new GameTimer with specified fall speed.
     *
     * @param fallSpeedMs Time between automatic drops in milliseconds
     * @param handler Handler for timer events
     */
    public GameTimer(int fallSpeedMs, MoveHandler handler) {
        this.moveHandler = handler;
        this.timeline = new Timeline(new KeyFrame(
                Duration.millis(fallSpeedMs),
                event -> moveHandler.onTimerTick()
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the timer.
     */
    public void start() {
        timeline.play();
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        timeline.stop();
    }

    /**
     * Pauses the timer.
     */
    public void pause() {
        timeline.pause();
    }

    /**
     * Resumes the timer after pause.
     */
    public void resume() {
        timeline.play();
    }

    /**
     * Checks if timer is currently running.
     */
    public boolean isRunning() {
        return timeline.getStatus() == Timeline.Status.RUNNING;
    }
}