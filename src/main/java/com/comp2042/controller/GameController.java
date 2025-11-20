package com.comp2042.controller;

import com.comp2042.*;
import com.comp2042.model.game.Board;
import com.comp2042.model.game.SimpleBoard;
import com.comp2042.view.GuiController;
import javafx.stage.Stage; //added for menu screen



public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);


    private final Stage stage;
    private final MenuController menuController;

    private final GuiController viewGuiController;


    public GameController(GuiController c, Stage stage, MenuController menuController) {
        this.stage = stage;
        this.menuController = menuController;
        viewGuiController = c;

        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }


        //Edited the onDownEvent
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();

            // Check game over AFTER merging
            if (((SimpleBoard)board).isGameOverCondition()) {
                viewGuiController.gameOver();
                return new DownData(clearRow, board.getViewData());
            }

            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }

            board.createNewBrick();
            viewGuiController.refreshGameBackground(board.getBoardMatrix());
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }


    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
