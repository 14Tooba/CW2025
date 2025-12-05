package com.comp2042.view.GUI;

import com.comp2042.view.ColorMapper;
import com.comp2042.view.RectangleRenderer;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import com.comp2042.Data.ViewData;
import com.comp2042.constants.GameConstants;
import java.awt.Point;

/**
 * Manages rendering of current and ghost bricks.
 *
 * @author Tooba Nauman
 * @version 1.0
 * @since 2025
 */
public class BrickRenderer {

    private static final int BRICK_SIZE = GameConstants.BRICK_SIZE;

    private final GridPane gamePanel;
    private final GridPane brickPanel;
    private GridPane ghostBrickPanel;
    private Rectangle[][] rectangles;
    private Rectangle[][] ghostRectangles;
    private boolean showGhost = true;

    public BrickRenderer(GridPane gamePanel, GridPane brickPanel, Pane parentPane) {
        this.gamePanel = gamePanel;
        this.brickPanel = brickPanel;
        initializeGhostPanel(parentPane);
    }

    public void initializeBrickDisplay(int[][] brickData) {
        rectangles = new Rectangle[brickData.length][brickData[0].length];
        for (int i = 0; i < brickData.length; i++) {
            for (int j = 0; j < brickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(ColorMapper.getColor(brickData[i][j]));
                rectangle.setOpacity(1.0);
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
    }

    private void initializeGhostPanel(Pane parentPane) {
        ghostBrickPanel = new GridPane();
        ghostBrickPanel.setHgap(1);
        ghostBrickPanel.setVgap(1);
        ghostBrickPanel.setStyle("-fx-background-color: transparent;");

        parentPane.getChildren().add(ghostBrickPanel);
    }

    private void initializeGhostRectangles(int[][] brickData) {
        ghostBrickPanel.getChildren().clear();

        ghostRectangles = new Rectangle[brickData.length][brickData[0].length];
        for (int i = 0; i < brickData.length; i++) {
            for (int j = 0; j < brickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setOpacity(0.4); // Ghost opacity
                ghostRectangles[i][j] = rectangle;
                ghostBrickPanel.add(rectangle, j, i);
            }
        }
    }

    public void refreshBrick(ViewData brick) {
        if (brick == null) return;

        // Position current brick
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap()
                + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap()
                + brick.getyPosition() * BRICK_SIZE);

        // Update current brick colors - FULL BRIGHTNESS
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                RectangleRenderer.styleRectangle(rectangles[i][j], brick.getBrickData()[i][j]);
                rectangles[i][j].setOpacity(1.0);
            }
        }

        // Update ghost brick
        updateGhostBrick(brick);
    }

    private void updateGhostBrick(ViewData brick) {
        if (!showGhost || brick.getGhostPosition() == null) {
            if (ghostBrickPanel != null) {
                ghostBrickPanel.setVisible(false);
            }
            return;
        }

        // Reinitialize if needed
        if (ghostRectangles == null ||
                ghostRectangles.length != brick.getBrickData().length ||
                ghostRectangles[0].length != brick.getBrickData()[0].length) {
            initializeGhostRectangles(brick.getBrickData());
        }

        Point ghostPos = brick.getGhostPosition();

        // Position ghost panel
        ghostBrickPanel.setLayoutX(gamePanel.getLayoutX() + ghostPos.getX() * brickPanel.getVgap()
                + ghostPos.getX() * BRICK_SIZE);
        ghostBrickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + ghostPos.getY() * brickPanel.getHgap()
                + ghostPos.getY() * BRICK_SIZE);

        // Set ghost colors - SAME colors as brick but with opacity
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                if (brick.getBrickData()[i][j] != 0) {
                    Color brickColor = (Color) ColorMapper.getColor(brick.getBrickData()[i][j]);
                    ghostRectangles[i][j].setFill(brickColor);
                    ghostRectangles[i][j].setOpacity(0.4); // Ghost transparency
                    RectangleRenderer.styleRectangle(ghostRectangles[i][j], brick.getBrickData()[i][j]);
                } else {
                    ghostRectangles[i][j].setFill(Color.TRANSPARENT);
                }
            }
        }

        ghostBrickPanel.setVisible(true);
        ghostBrickPanel.toBack(); // Send ghost behind current brick
    }

    public void toggleGhost() {
        showGhost = !showGhost;
        if (ghostBrickPanel != null) {
            ghostBrickPanel.setVisible(showGhost);
        }
    }

    public boolean isGhostShown() {
        return showGhost;
    }
}