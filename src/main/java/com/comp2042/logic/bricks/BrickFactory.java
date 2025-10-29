package com.comp2042.logic.bricks;

/**
 * Factory class for creating brick instances.
 * Implements Factory Design Pattern to centralize brick creation.
 */
public class BrickFactory {

    /**
     * Enum representing all available brick types.
     */
    public enum BrickType {
        I_BRICK,
        J_BRICK,
        L_BRICK,
        O_BRICK,
        S_BRICK,
        T_BRICK,
        Z_BRICK
    }

    /**
     * Creates a new brick based on type.
     */
    public static Brick createBrick(BrickType type) {
        if (type == null) {
            throw new IllegalArgumentException("Brick type cannot be null");
        }

        return switch (type) {
            case I_BRICK -> new IBrick();
            case J_BRICK -> new JBrick();
            case L_BRICK -> new LBrick();
            case O_BRICK -> new OBrick();
            case S_BRICK -> new SBrick();
            case T_BRICK -> new TBrick();
            case Z_BRICK -> new ZBrick();
        };
    }

    /**
     * Gets total number of brick types.
     */
    public static int getBrickTypeCount() {
        return BrickType.values().length;
    }

    /**
     * Gets brick type by index (useful for random generation).
     */
    public static BrickType getBrickTypeByIndex(int index) {
        BrickType[] types = BrickType.values();
        if (index < 0 || index >= types.length) {
            throw new IllegalArgumentException("Invalid brick type index: " + index);
        }
        return types[index];
    }
}