package com.comp2042.view;

import com.comp2042.*;
import com.comp2042.controller.GameController;
import com.comp2042.controller.InputEventListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.control.Label;

import java.awt.*;

import com.comp2042.utils.SoundManager; //for SoundManager
import com.comp2042.view.PauseMenu; //for PauseMenu
import javafx.stage.Stage;
import com.comp2042.controller.MenuController;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.control.Label; //for score display
import com.comp2042.model.game.LavaManager;
import com.comp2042.model.game.SimpleBoard;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    //for score tracking
    @FXML
    private Label scoreLabel;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Timeline timeLine;
    private GameTimer gameTimer;

    //for SoundManager
    private SoundManager soundManager;

    //for PauseMenu
    private PauseMenu pauseMenu;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    //adding new fields for ghostBrickCalculator
    private GridPane ghostBrickPanel;
    private Rectangle[][] ghostRectangles;
    private boolean showGhost = true;

    //added for menu screen
    private Stage stage;
    private MenuController menuController;

    private LevelUpNotification levelUpNotification;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        soundManager = new SoundManager();
        // Create pause menu
        pauseMenu = new PauseMenu();
        pauseMenu.setPrefSize(300, 510);
        pauseMenu.setLayoutX(0);
        pauseMenu.setLayoutY(0);

        pauseMenu.getResumeButton().setOnAction(e -> resumeGame());
        pauseMenu.getQuitButton().setOnAction(e -> {
            System.exit(0); // Quit the game
        });
        // Create level up notification
        levelUpNotification = new LevelUpNotification("Next Level");
        levelUpNotification.setPrefSize(300, 510);
        levelUpNotification.setLayoutX(0);
        levelUpNotification.setLayoutY(0);


        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        ViewData result = eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER));
                        soundManager.playRotate();
                        refreshBrick(result);
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }

                    //adding code for G key, GhostBrickCalculator
                    if (keyEvent.getCode() == KeyCode.G) {
                        showGhost = !showGhost;
                        if (showGhost) {
                            ghostBrickPanel.setVisible(true);
                        } else {
                            ghostBrickPanel.setVisible(false);
                        }
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
                if (keyEvent.getCode() == KeyCode.P || keyEvent.getCode() == KeyCode.ESCAPE) {
                    togglePause();
                    keyEvent.consume();
                }
            }
        });
        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }


    //menu
    public void setStageAndMenu(Stage stage, MenuController menuController) {
        this.stage = stage;
        this.menuController = menuController;
    }


    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);



        // Initialize ghost brick panel
        ghostBrickPanel = new GridPane();
        ghostBrickPanel.setHgap(brickPanel.getHgap());
        ghostBrickPanel.setVgap(brickPanel.getVgap());

        ghostRectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(javafx.scene.paint.Color.TRANSPARENT);
                rectangle.setOpacity(0.5);
                ghostRectangles[i][j] = rectangle;
                ghostBrickPanel.add(rectangle, j, i);
            }
        }
//adding the ghost rectangles to the scene
        ((javafx.scene.layout.Pane) gamePanel.getParent()).getChildren().add(ghostBrickPanel);



        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();

        // Add pause menu to scene
        pauseMenu.setPrefWidth(300);
        pauseMenu.setPrefHeight(510);
        pauseMenu.setLayoutX(0);
        pauseMenu.setLayoutY(0);
        ((javafx.scene.layout.Pane) gamePanel.getParent()).getChildren().add(pauseMenu);

        // Add level up notification to scene
        ((javafx.scene.layout.Pane) gamePanel.getParent()).getChildren().add(levelUpNotification);

        //initialize GameTimer method
        gameTimer = new GameTimer(400, () -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD)));
        gameTimer.start();
    }


    //calling the new method created for the colorMapper instead of switch method
    private Paint getFillColor(int i) {
        return ColorMapper.getColor(i);
    }


    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
        }

        // Update ghost brick position
        if (showGhost && brick.getGhostPosition() != null) {
            Point ghostPos = brick.getGhostPosition();
            ghostBrickPanel.setLayoutX(gamePanel.getLayoutX() + ghostPos.getX() * brickPanel.getVgap() + ghostPos.getX() * BRICK_SIZE);
            ghostBrickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + ghostPos.getY() * brickPanel.getHgap() + ghostPos.getY() * BRICK_SIZE);

            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    if (brick.getBrickData()[i][j] != 0) {
                        ghostRectangles[i][j].setFill(ColorMapper.getColor(brick.getBrickData()[i][j]));
                        ghostRectangles[i][j].setOpacity(0.5);
                    } else {
                        ghostRectangles[i][j].setFill(javafx.scene.paint.Color.TRANSPARENT);
                    }
                }
            }
            ghostBrickPanel.setVisible(true);
        } else {
            if (ghostBrickPanel != null) {
                ghostBrickPanel.setVisible(false);
            }
        }
    }


    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }

        // Render lava if active -pass lava info separately
    }


    /**
     * Updates lava rendering on the board.
     *
     * @param lavaRows Array of rows occupied by lava
     */
    public void updateLavaDisplay(int[] lavaRows) {
        // First, clear any previous lava rendering from all rows
        for (int i = 2; i < displayMatrix.length; i++) {
            for (int j = 0; j < displayMatrix[i].length; j++) {
                // Only clear if it's empty (not a placed block)
                if (displayMatrix[i][j].getFill() == Color.rgb(255, 69, 0) ||
                        displayMatrix[i][j].getFill() == Color.rgb(255, 140, 0)) {
                    displayMatrix[i][j].setFill(Color.TRANSPARENT);
                }
            }
        }

        // Render all lava rows in bright orange
        for (int lavaRow : lavaRows) {
            if (lavaRow >= 2 && lavaRow < displayMatrix.length) {
                for (int j = 0; j < displayMatrix[lavaRow].length; j++) {
                    // Bright orange lava
                    displayMatrix[lavaRow][j].setFill(Color.rgb(255, 69, 0)); // Bright orange-red
                    displayMatrix[lavaRow][j].setArcHeight(9);
                    displayMatrix[lavaRow][j].setArcWidth(9);
                }
            }
        }
    }




    //calling the method for rectangle rendering as its own class
    private void setRectangleData(int color, Rectangle rectangle) {
        RectangleRenderer.styleRectangle(rectangle, color);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                soundManager.playLineClear();
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }


    //updated for displaying the score while playing the game.
    public void bindScore(IntegerProperty integerProperty) {
        if (scoreLabel != null) {
            scoreLabel.textProperty().bind(integerProperty.asString());
        }
    }


    public void gameOver() {
        soundManager.playGameOver();
        timeLine.stop();
        if (gameTimer != null) gameTimer.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);

        // Return to menu after 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> returnToMenu());
        pause.play();
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        //additional for game timer conditions
        if (gameTimer != null) gameTimer.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        if (gameTimer != null) gameTimer.start();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }
    /**
     * Shows level up notification with level name.
     * Pauses game briefly to show notification.
     *
     * @param levelName Name of the next level
     */
    public void showLevelUp(String levelName) {
        System.out.println("SHOWING LEVEL UP: " + levelName);

        // Get parent pane
        javafx.scene.layout.Pane parent = (javafx.scene.layout.Pane) gamePanel.getParent();

        // Remove old notification if it exists
        if (levelUpNotification != null) {
            parent.getChildren().remove(levelUpNotification);
        }

        // Create notification
        levelUpNotification = new LevelUpNotification(levelName);
        levelUpNotification.setPrefWidth(300);
        levelUpNotification.setPrefHeight(510);

        // Add to parent and bring to absolute front
        parent.getChildren().add(levelUpNotification);
        levelUpNotification.toFront();

        // Pause the game
        timeLine.pause();
        if (gameTimer != null) gameTimer.pause();

        // Show for 3 seconds then hide and resume
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            parent.getChildren().remove(levelUpNotification);
            timeLine.play();
            if (gameTimer != null) gameTimer.start();
            gamePanel.requestFocus();
        });
        pause.play();
    }


    /**
     * Toggles pause state.
     */
    private void togglePause() {
        if (isGameOver.getValue()) {
            return; // Don't pause if game is over
        }

        isPause.setValue(!isPause.getValue());

        if (isPause.getValue()) {
            // Pausing
            timeLine.pause();
            if (gameTimer != null) gameTimer.pause();
            pauseMenu.setVisible(true);
        } else {
            // Resuming
            resumeGame();
        }
    }

    /**
     * Resumes the game from pause.
     */
    private void resumeGame() {
        isPause.setValue(false);
        pauseMenu.setVisible(false);
        timeLine.play();
        if (gameTimer != null) gameTimer.resume();
        gamePanel.requestFocus();
    }


    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
    }

//returning the main menu after game is over.
    private void returnToMenu() {
        if (stage != null && menuController != null) {
            stage.setScene(menuController.getMenuScene());
        }
    }
}
