package com.comp2042.view;

import javafx.scene.shape.Rectangle;

/**
 * Helper class for rendering game rectangles.
 * Single responsibility: Rectangle styling and rendering
 */
public final class RectangleRenderer {

    private RectangleRenderer() {}

    /**
     * this now styles a rectangle with color and rounded corners.
     */
    public static void styleRectangle(Rectangle rectangle, int colorCode) {
        rectangle.setFill(ColorMapper.getColor(colorCode));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }
}