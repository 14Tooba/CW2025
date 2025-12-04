package com.comp2042.controller;

import com.comp2042.DownData;
import com.comp2042.MoveEvent;
import com.comp2042.ViewData;

/**
 * Interface defining callback methods for game input events.
 * Implemented by controllers that need to respond to user input
 * and game timer events during Tetris gameplay.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public interface InputEventListener {

    /**
     * Called when a downward movement event occurs.
     *
     * @param event The move event containing event type and source
     * @return DownData containing clear row information and view data
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Called when a left movement event occurs.
     *
     * @param event The move event containing event type and source
     * @return ViewData containing updated brick position and display data
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Called when a right movement event occurs.
     *
     * @param event The move event containing event type and source
     * @return ViewData containing updated brick position and display data
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Called when a rotation event occurs.
     *
     * @param event The move event containing event type and source
     * @return ViewData containing updated brick orientation and display data
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Called to initialize a new game session.
     * Resets the board and game state to default values.
     */
    void createNewGame();
}
