package com.comp2042.model.game;

import com.comp2042.ClearRow;
import com.comp2042.ViewData;
import com.comp2042.model.scoring.Score;
import com.comp2042.model.game.GameLevel;
import com.comp2042.model.game.LavaManager;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateLeftBrick();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    Score getScore();

    void newGame();

    GameLevel getCurrentLevel();
    LavaManager getLavaManager();
    boolean shouldLevelUp();
    void advanceToNextLevel();
    boolean checkLavaGameOver();

    /**
     * Gets the TargetChallengeManager instance.
     * @return TargetChallengeManager
     */
    TargetChallengeManager getTargetChallengeManager();

    /**
     * Checks if target challenge timer has expired.
     * @return true if time is up
     */
    boolean checkTargetChallengeTimeout();
}
