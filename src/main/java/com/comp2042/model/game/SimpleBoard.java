package com.comp2042.model.game;

import com.comp2042.ClearRow;
import com.comp2042.NextShapeInfo;
import com.comp2042.Data.ViewData;
import com.comp2042.constants.GameConstants;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.model.scoring.Score;
import com.comp2042.utils.MatrixOperations;
import java.util.HashSet;
import java.util.Set;

import java.awt.*;


/**
 * Main implementation of the Tetris game board with multi-level support.
 * Manages brick placement, collision detection, line clearing, and level-specific mechanics.
 * Integrates LavaManager for Lava Survival mode and TargetChallengeManager for mission-based gameplay.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;
    private final LavaManager lavaManager;
    private GameLevel currentLevel = GameLevel.CLASSIC;
    private int levelLinesCleared = 0;
    private final TargetChallengeManager targetChallengeManager;



    /**
     * Constructs a SimpleBoard with specified dimensions.
     * Initializes game matrix, brick generator, rotator, score tracker,
     * and level-specific managers.
     *
     * @param width The height of the game board (number of rows)
     * @param height The width of the game board (number of columns)
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
        this.lavaManager = new LavaManager();
        this.targetChallengeManager = new TargetChallengeManager(); //initialize for target challenge//initialize for new level
    }




    /**
     * Attempts to move the current brick down by one row.
     * Checks for collision with board boundaries and existing blocks.
     *
     * @return true if brick moved successfully, false if collision detected
     */
    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }



    /**
     * Attempts to move the current brick left by one column.
     * Checks for collision with board boundaries and existing blocks.
     *
     * @return true if brick moved successfully, false if collision detected
     */
    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }


    /**
     * Attempts to move the current brick right by one column.
     * Checks for collision with board boundaries and existing blocks.
     *
     * @return true if brick moved successfully, false if collision detected
     */
    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }


    /**
     * Attempts to rotate the current brick counter-clockwise.
     * Checks for collision at the new orientation before applying rotation.
     *
     * @return true if rotation successful, false if collision detected
     */
    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }


    /**
     * Spawns a new brick at the top of the board.
     * Retrieves next brick from generator and positions it at spawn point.
     *
     * @return true if spawn position is blocked (game over condition), false otherwise
     */
    //Updated so that the spawn position of the bricks is now at the top
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(
                GameConstants.BRICK_SPAWN_X,
                GameConstants.BRICK_SPAWN_Y
        );

        // Return true if spawn position is blocked (game over condition)
        return MatrixOperations.intersect(
                currentGameMatrix,
                brickRotator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY()
        );
    }


    /**
     * Retrieves the current game board matrix.
     *
     * @return 2D integer array representing the board state
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }



    /**
     * Constructs view data for rendering the current game state.
     * Includes current brick position, shape, next brick preview, and ghost position.
     *
     * @return ViewData object containing all rendering information
     */
    //updated the simple board so that it can calculate the ghost position.
    @Override
    public ViewData getViewData() {
        // Calculate ghost brick position
        Point ghostPos = GhostBrickCalculator.calculateLandingPosition(
                currentGameMatrix,
                brickRotator.getCurrentShape(),
                currentOffset
        );

        return new ViewData(
                brickRotator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY(),
                brickGenerator.getNextBrick().getShapeMatrix().get(0),
                ghostPos
        );
    }

    /**
     * Merges the current brick into the board's background matrix.
     * Called when brick lands and becomes part of the static board.
     */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }


    /**
     * Checks for and clears any complete rows on the board.
     * Updates level-specific managers and calculates score bonuses.
     * Handles lava updates and target block tracking for special modes.
     *
     * @return ClearRow object containing lines removed count, new matrix, and score bonus
     */
    //updated for the new lava level
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();

        if (clearRow.getLinesRemoved() > 0) {
            levelLinesCleared += clearRow.getLinesRemoved();

            // Record line clear in lava mode
            if (lavaManager.isActive()) {
                lavaManager.recordLineClear();
                // Update target blocks if in target challenge mode
                if (targetChallengeManager.isActive()) {
                    Set<Point> clearedPositions = new HashSet<>();
                    // Add logic to track which positions were cleared
                    // This would need to be extracted from the clearRow operation
                    targetChallengeManager.updateTargetBlocks(clearedPositions);
                }
            }
        }

        // Update lava position if active
        if (lavaManager.isActive()) {
            lavaManager.update();
        }

        return clearRow;
    }


    /**
     * Retrieves the current score tracker.
     *
     * @return Score object tracking player's current score
     */
    @Override
    public Score getScore() {
        return score;
    }


    /**
     * Resets the board for a new game session.
     * Clears matrix, resets score, returns to Classic level, and spawns first brick.
     */
    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        currentLevel = GameLevel.CLASSIC;  // update for lava game
        levelLinesCleared = 0;             // update for lava game
        lavaManager.reset();               // update for lava game
        createNewBrick();
    }

    /**
     * Checks if any blocks exist in the spawn area (top 2 hidden rows).
     * This indicates game over.
     */
    public boolean isGameOverCondition() {
        // Check rows 0 and 1 (spawn area)
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < currentGameMatrix[row].length; col++) {
                if (currentGameMatrix[row][col] != 0) {
                    return true; // Block in spawn area = game over
                }
            }
        }
        return false;
    }


    /**
     * Gets current game level.
     *
     * @return Current level
     */
    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Gets lava manager instance.
     *
     * @return LavaManager
     */
    public LavaManager getLavaManager() {
        return lavaManager;
    }

    /**
     * Checks if player should advance to next level.
     *
     * @return true if ready to level up
     */
    public boolean shouldLevelUp() {
        if (currentLevel == GameLevel.TARGET_CHALLENGE) {
            return targetChallengeManager.isMissionComplete();
        }
        return levelLinesCleared >= currentLevel.getLinesRequiredToAdvance();
    }

    /**
     * Advances to the next level and resets board.
     */
    public void advanceToNextLevel() {
        currentLevel = currentLevel.getNextLevel();
        levelLinesCleared = 0;

        // Clear the board
        currentGameMatrix = new int[width][height];

        // Deactivate previous level features
        lavaManager.deactivate();
        targetChallengeManager.deactivate();

        // Activate features for new level
        if (currentLevel == GameLevel.LAVA_SURVIVAL) {
            lavaManager.activate();
        } else if (currentLevel == GameLevel.TARGET_CHALLENGE) {
            targetChallengeManager.activate();
            targetChallengeManager.generatePattern(currentGameMatrix, width, height);
        }

        // Create new brick for the new level
        createNewBrick();
    }

    /**
     * Checks if lava has collided with blocks (game over condition).
     *
     * @return true if lava touched blocks
     */
    public boolean checkLavaGameOver() {
        return lavaManager.isActive() && lavaManager.checkLavaCollision(currentGameMatrix);
    }


    /**
     * Gets the TargetChallengeManager instance.
     *
     * @return TargetChallengeManager
     */
    public TargetChallengeManager getTargetChallengeManager() {
        return targetChallengeManager;
    }

    /**
     * Checks if target challenge timer has expired.
     *
     * @return true if time is up
     */
    public boolean checkTargetChallengeTimeout() {
        return targetChallengeManager.isActive() && targetChallengeManager.updateTimer();
    }
}
