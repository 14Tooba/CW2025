package com.comp2042.Data;

import com.comp2042.utils.MatrixOperations;
import java.awt.Point;

public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;
    private final Point ghostPosition;   //added for ghost brick calculator

//updated the view data for Ghost brick calculator
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, Point ghostPosition) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.ghostPosition = ghostPosition;
    }

    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }

    //adding the getter method for GhostBrick calculator
    public Point getGhostPosition() {
        return ghostPosition != null ? new Point(ghostPosition) : null;
    }
}
