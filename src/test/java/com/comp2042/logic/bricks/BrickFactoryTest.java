package com.comp2042.logic.bricks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BrickFactoryTest {

    @Test
    @DisplayName("Factory creates correct brick types")
    void testCreateBrick_AllTypes() {
        assertNotNull(BrickFactory.createBrick(BrickFactory.BrickType.I_BRICK));
        assertNotNull(BrickFactory.createBrick(BrickFactory.BrickType.J_BRICK));
        assertNotNull(BrickFactory.createBrick(BrickFactory.BrickType.L_BRICK));
        assertNotNull(BrickFactory.createBrick(BrickFactory.BrickType.O_BRICK));
        assertNotNull(BrickFactory.createBrick(BrickFactory.BrickType.S_BRICK));
        assertNotNull(BrickFactory.createBrick(BrickFactory.BrickType.T_BRICK));
        assertNotNull(BrickFactory.createBrick(BrickFactory.BrickType.Z_BRICK));
    }

    @Test
    @DisplayName("Factory throws exception for null type")
    void testCreateBrick_Null() {
        assertThrows(IllegalArgumentException.class,
                () -> BrickFactory.createBrick(null));
    }

    @Test
    @DisplayName("Brick type count is 7")
    void testGetBrickTypeCount() {
        assertEquals(7, BrickFactory.getBrickTypeCount());
    }

    @Test
    @DisplayName("Valid indices work")
    void testGetBrickTypeByIndex_Valid() {
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            assertDoesNotThrow(() -> BrickFactory.getBrickTypeByIndex(finalI));
        }
    }

    @Test
    @DisplayName("Invalid indices throw exception")
    void testGetBrickTypeByIndex_Invalid() {
        assertThrows(IllegalArgumentException.class,
                () -> BrickFactory.getBrickTypeByIndex(-1));
        assertThrows(IllegalArgumentException.class,
                () -> BrickFactory.getBrickTypeByIndex(7));
    }

    @Test
    @DisplayName("Factory creates new instances each time")
    void testNewInstances() {
        Brick b1 = BrickFactory.createBrick(BrickFactory.BrickType.I_BRICK);
        Brick b2 = BrickFactory.createBrick(BrickFactory.BrickType.I_BRICK);
        assertNotSame(b1, b2);
    }

    @Test
    @DisplayName("All bricks have shape matrices")
    void testAllBricksHaveShapes() {
        for (BrickFactory.BrickType type : BrickFactory.BrickType.values()) {
            Brick brick = BrickFactory.createBrick(type);
            assertNotNull(brick.getShapeMatrix());
            assertFalse(brick.getShapeMatrix().isEmpty());
        }
    }
}