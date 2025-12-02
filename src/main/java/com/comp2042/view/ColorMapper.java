package com.comp2042.view;

import javafx.scene.paint.Color;

/**
 * Maps color codes to vibrant JavaFX colors for game elements.
 * Provides bold, high-contrast colors for better visibility.
 */
public final class ColorMapper {
    private ColorMapper() {}

    /**
     * Gets vibrant color for given code.
     * @param colorCode Color code (0-7)
     * @return Bold JavaFX Color
     */
    public static Color getColor(int colorCode) {
        return switch (colorCode) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.rgb(255, 50, 50);     // Bright Red
            case 2 -> Color.rgb(50, 255, 50);     // Bright Green
            case 3 -> Color.rgb(50, 150, 255);    // Bright Blue
            case 4 -> Color.rgb(255, 200, 50);    // Golden Yellow
            case 5 -> Color.rgb(255, 50, 255);    // Magenta
            case 6 -> Color.rgb(50, 255, 255);    // Cyan
            case 7 -> Color.rgb(255, 150, 50);    // Orange (for target blocks)
            default -> Color.WHITE;
        };
    }
}