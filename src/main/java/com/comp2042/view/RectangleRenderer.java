package com.comp2042.view;

import javafx.scene.shape.Rectangle;

/**
 * Helper class for rendering game rectangles.
 */
public final class RectangleRenderer {

    private RectangleRenderer() {}

    /**
     * Styles a rectangle with color and rounded corners.
     * Sets FULL OPACITY for regular bricks.
     */
    public static void styleRectangle(Rectangle rectangle, int colorCode) {
        rectangle.setFill(ColorMapper.getColor(colorCode));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }
}