package com.comp2042.controller;

import com.comp2042.*;
import com.comp2042.Data.DownData;
import com.comp2042.Data.ViewData;
import com.comp2042.model.game.Board;
import com.comp2042.model.game.SimpleBoard;
import com.comp2042.view.GUI.GuiController;
import com.comp2042.view.LevelUpNotification;
import javafx.stage.Stage; //added for menu screen



/**
 * Main game controller implementing input event handling and game flow.
 * Coordinates between the game board model and GUI view components.
 * Manages level progression, score updates, and game state transitions.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */

public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);


    private final Stage stage;
    private final MenuController menuController;

    private final GuiController viewGuiController;

    private LevelUpNotification levelUpNotification;

    /**
     * Constructs a GameController with specified view, stage, and menu controller.
     * Initializes the game board, creates first brick, and sets up view bindings.
     *
     * @param c The GUI controller for view management
     * @param stage The primary stage for scene transitions
     * @param menuController The menu controller for navigation back to menu
     */

    public GameController(GuiController c, Stage stage, MenuController menuController) {
        this.stage = stage;
        this.menuController = menuController;
        viewGuiController = c;

        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    /**
     * Handles downward movement events for the current brick.
     * Processes brick landing, line clearing, level progression checks,
     * and game over conditions for all game modes.
     *
     * @param event The move event containing event type and source
     * @return DownData containing clear row information and updated view data
     */
        //Edited the onDownEvent
        @Override
        public DownData onDownEvent(MoveEvent event) {
            boolean canMove = board.moveBrickDown();
            ClearRow clearRow = null;

            // Check target challenge timeout
            if (board.checkTargetChallengeTimeout()) {
                viewGuiController.gameOver();
                return new DownData(clearRow, board.getViewData());
            }

            if (!canMove) {
                board.mergeBrickToBackground();

                // Check lava collision (game over in lava mode)
                if (board.checkLavaGameOver()) {
                    viewGuiController.gameOver();
                    return new DownData(clearRow, board.getViewData());
                }

                clearRow = board.clearRows();
                if (clearRow.getLinesRemoved() > 0) {
                    board.getScore().add(clearRow.getScoreBonus());
                }

                // Check if it should level up
                if (board.shouldLevelUp()) {
                    viewGuiController.showLevelUp(board.getCurrentLevel().getNextLevel().getDisplayName());
                    board.advanceToNextLevel();
                }

                if (board.createNewBrick()) {
                    viewGuiController.gameOver();
                }

                viewGuiController.refreshGameBackground(board.getBoardMatrix());

                // Update lava display if active
                if (board.getLavaManager().isActive()) {
                    viewGuiController.updateLavaDisplay(board.getLavaManager().getLavaRows());
                }

                // Update target challenge display if active
                if (board.getTargetChallengeManager().isActive()) {
                    viewGuiController.updateTargetChallengeDisplay(
                            board.getTargetChallengeManager().getRemainingTargetBlocks(),
                            board.getTargetChallengeManager().getFormattedTime(),
                            board.getTargetChallengeManager().getCurrentMission()
                    );
                }

            }

            return new DownData(clearRow, board.getViewData());
        }




    /**
     * Retrieves the current game board instance.
     *
     * @return The active Board instance
     */

    public Board getBoard() {
        return board;
    }

    /**
     * Handles left movement events for the current brick.
     *
     * @param event The move event containing event type and source
     * @return ViewData containing updated brick and ghost positions
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }


    /**
     * Handles right movement events for the current brick.
     *
     * @param event The move event containing event type and source
     * @return ViewData containing updated brick and ghost positions
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }


    /**
     * Handles rotation events for the current brick.
     * Attempts to rotate brick left with wall kick support.
     *
     * @param event The move event containing event type and source
     * @return ViewData containing updated brick and ghost positions
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    /**
     * Resets the game to initial state with a fresh board.
     * Clears all blocks and refreshes the game background display.
     */
    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
