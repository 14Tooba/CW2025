package com.comp2042.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ColorMapperTest {

    @Test
    @DisplayName("Empty code returns transparent")
    void testEmptyColor() {
        assertEquals(Color.TRANSPARENT, ColorMapper.getColor(0));
    }

    @Test
    @DisplayName("All brick colors are mapped")
    void testAllBrickColors() {
        assertEquals(Color.AQUA, ColorMapper.getColor(1));
        assertEquals(Color.BLUEVIOLET, ColorMapper.getColor(2));
        assertEquals(Color.DARKGREEN, ColorMapper.getColor(3));
        assertEquals(Color.YELLOW, ColorMapper.getColor(4));
        assertEquals(Color.RED, ColorMapper.getColor(5));
        assertEquals(Color.BEIGE, ColorMapper.getColor(6));
        assertEquals(Color.BURLYWOOD, ColorMapper.getColor(7));
    }

    @Test
    @DisplayName("Invalid code returns white")
    void testInvalidColor() {
        assertEquals(Color.WHITE, ColorMapper.getColor(99));
        assertEquals(Color.WHITE, ColorMapper.getColor(-1));
    }

    @Test
    @DisplayName("Color mapper returns non-null")
    void testNonNull() {
        for (int i = 0; i < 8; i++) {
            assertNotNull(ColorMapper.getColor(i));
        }
    }
}