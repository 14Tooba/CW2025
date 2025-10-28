package com.comp2042.controller;

import com.comp2042.DownData;
import com.comp2042.MoveEvent;
import com.comp2042.ViewData;

public interface InputEventListener {

    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateEvent(MoveEvent event);

    void createNewGame();
}
