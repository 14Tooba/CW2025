package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrickGenerator implements BrickGenerator {

    private final Deque<Brick> nextBricks = new ArrayDeque<>();
    private final int brickTypeCount;

    public RandomBrickGenerator() {
        this.brickTypeCount = BrickFactory.getBrickTypeCount();
        // Pre-generate two bricks for preview
        generateNextBrick();
        generateNextBrick();
    }

    @Override
    public Brick getBrick() {
        // Ensure we always have at least one brick in queue
        if (nextBricks.size() <= 1) {
            generateNextBrick();
        }
        return nextBricks.poll();
    }

    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }

    /**
     * Generates a new random brick and adds it to the queue.
     */
    private void generateNextBrick() {
        int randomIndex = ThreadLocalRandom.current().nextInt(brickTypeCount);
        BrickFactory.BrickType type = BrickFactory.getBrickTypeByIndex(randomIndex);
        Brick newBrick = BrickFactory.createBrick(type);
        nextBricks.add(newBrick);
    }
}
