package com.comp2042.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Maps brick color codes to Paint colors.
 * Single responsibility: Color mapping only.
 */
public final class ColorMapper {

    private ColorMapper() {}

    public static Paint getColor(int code) {
        return switch (code) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }
}