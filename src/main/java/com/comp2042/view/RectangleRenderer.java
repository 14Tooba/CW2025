package com.comp2042.view;

import javafx.scene.shape.Rectangle;

/**
 * Utility class for styling game board rectangle cells.
 * Applies colors, opacity, and rounded corners based on block type.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
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