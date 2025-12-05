package com.comp2042.view;

import com.comp2042.*;
import com.comp2042.controller.GameController;
import com.comp2042.controller.InputEventListener;
import com.comp2042.model.scoring.HighScoreManager;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.comp2042.controller.MenuController;
import com.comp2042.utils.SoundManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Refactored main GUI controller for the Tetris game.
 * Coordinates between specialized UI component managers.
 *
 * @author Tooba Nauman
 * @version 2.0
 * @since 2025
 */
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

    @FXML
    private Label scoreLabel;

    @FXML
    private VBox targetChallengeContainer;

    @FXML
    private Label missionLabel;

    @FXML
    private Label targetBlocksLabel;

    @FXML
    private Label timerLabel;

    // Specialized components
    private GameBoardRenderer boardRenderer;
    private BrickRenderer brickRenderer;
    private LavaDisplayManager lavaDisplayManager;
    private TargetChallengeUI targetChallengeUI;
    private GameInputHandler inputHandler;

    // Game state
    private InputEventListener eventListener;
    private Timeline timeLine;
    private GameTimer gameTimer;
    private SoundManager soundManager;
    private PauseMenu pauseMenu;
    private LevelUpNotification levelUpNotification;

    private final BooleanProperty isPause = new SimpleBooleanProperty(false);
    private final BooleanProperty isGameOver = new SimpleBooleanProperty(false);

    private Stage stage;
    private MenuController menuController;
    private HighScoreManager highScoreManager = new HighScoreManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);

        soundManager = new SoundManager();
        soundManager.startBackgroundMusic();

        pauseMenu = new PauseMenu();
        pauseMenu.setVisible(false);
        pauseMenu.setTranslateX(20);
        pauseMenu.setTranslateY(120);
        gamePanel.getChildren().add(pauseMenu);

        pauseMenu.getResumeButton().setOnAction(e -> {
            resumeGame();
            gamePanel.requestFocus();
        });
        pauseMenu.getQuitButton().setOnAction(e -> System.exit(0));

        levelUpNotification = new LevelUpNotification("Next Level");
        levelUpNotification.setPrefSize(300, 510);
        levelUpNotification.setLayoutX(0);
        levelUpNotification.setLayoutY(0);

        targetChallengeUI = new TargetChallengeUI(
                targetChallengeContainer,
                missionLabel,
                targetBlocksLabel,
                timerLabel
        );

        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();

        gameOverPanel.setVisible(false);
    }

    public void setStageAndMenu(Stage stage, MenuController menuController) {
        this.stage = stage;
        this.menuController = menuController;
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        boardRenderer = new GameBoardRenderer(gamePanel);
        boardRenderer.initializeBoard(boardMatrix);

        brickRenderer = new BrickRenderer(gamePanel, brickPanel,
                (javafx.scene.layout.Pane) gamePanel.getParent());
        brickRenderer.initializeBrickDisplay(brick.getBrickData());

        lavaDisplayManager = new LavaDisplayManager(gamePanel, boardRenderer.getDisplayMatrix());

        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap()
                + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap()
                + brick.getyPosition() * BRICK_SIZE);

        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();

        pauseMenu.setPrefWidth(300);
        pauseMenu.setPrefHeight(510);
        pauseMenu.setLayoutX(0);
        pauseMenu.setLayoutY(0);
        ((javafx.scene.layout.Pane) gamePanel.getParent()).getChildren().add(pauseMenu);

        ((javafx.scene.layout.Pane) gamePanel.getParent()).getChildren().add(levelUpNotification);

        gameTimer = new GameTimer(400, () -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD)));
        gameTimer.start();
    }

    private void moveDown(MoveEvent event) {
        if (!isPause.getValue()) {
            DownData downData = eventListener.onDownEvent(event);

            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                soundManager.playLineClear();
                NotificationPanel notificationPanel = new NotificationPanel(
                        "+" + downData.getClearRow().getScoreBonus()
                );
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }

            brickRenderer.refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;

        if (eventListener instanceof GameController) {
            targetChallengeUI.setGameController((GameController) eventListener);
        }

        inputHandler = new GameInputHandler(eventListener, this);

        // Set up keyboard handling
        gamePanel.setOnKeyPressed(keyEvent -> {
            KeyCode code = keyEvent.getCode();

            // Handle DOWN key for soft drop (faster falling)
            if (code == KeyCode.DOWN || code == KeyCode.S) {
                if (!isPause.getValue() && !isGameOver.getValue()) {
                    moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                    keyEvent.consume();
                }
            }
            // Handle SPACE key for hard drop (instant drop)
            else if (code == KeyCode.SPACE) {
                if (!isPause.getValue() && !isGameOver.getValue()) {
                    hardDrop();
                    keyEvent.consume();
                }
            }
            // Delegate other keys to input handler
            else {
                inputHandler.handle(keyEvent);
            }
        });
    }

    private void hardDrop() {
        // Get current view data to find ghost position
        ViewData currentView = eventListener.onDownEvent(
                new MoveEvent(EventType.DOWN, EventSource.USER)
        ).getViewData();

        if (currentView != null && currentView.getGhostPosition() != null) {
            // Keep moving down until we reach ghost position
            while (true) {
                DownData downData = eventListener.onDownEvent(
                        new MoveEvent(EventType.DOWN, EventSource.USER)
                );

                if (downData.getClearRow() != null) {
                    // Brick has landed - line clear occurred
                    brickRenderer.refreshBrick(downData.getViewData());
                    break;
                }

                // Check if we can't move anymore
                if (!eventListener.getBoard().moveBrickDown()) {
                    break;
                }
            }
        }

        soundManager.playDrop();
        gamePanel.requestFocus();
    }

    // Public methods for GameInputHandler callbacks
    public void handleLeftMove() {
        ViewData result = eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER));
        brickRenderer.refreshBrick(result);
    }

    public void handleRightMove() {
        ViewData result = eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER));
        brickRenderer.refreshBrick(result);
    }

    public void handleRotate() {
        ViewData result = eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER));
        soundManager.playRotate();
        brickRenderer.refreshBrick(result);
    }

    public void handleGhostToggle() {
        brickRenderer.toggleGhost();
    }

    public void handleNewGame() {
        newGame(null);
    }

    public void handleTogglePause() {
        togglePause();
    }

    public void bindScore(IntegerProperty integerProperty) {
        if (scoreLabel != null) {
            scoreLabel.textProperty().bind(integerProperty.asString());
        }
    }

    public void refreshGameBackground(int[][] board) {
        boardRenderer.refreshGameBackground(board);
    }

    public void updateLavaDisplay(int[] lavaRows) {
        lavaDisplayManager.updateLavaDisplay(lavaRows);
    }

    public void updateTargetChallengeDisplay(int remainingBlocks, String formattedTime,
                                             com.comp2042.model.game.TargetChallengeManager.MissionType mission) {
        targetChallengeUI.updateDisplay(remainingBlocks, formattedTime, mission);
    }

    public void gameOver() {
        soundManager.playGameOver();
        soundManager.stopBackgroundMusic();

        timeLine.stop();
        if (gameTimer != null) {
            gameTimer.stop();
        }

        gameOverPanel.setVisible(true);
        isGameOver.setValue(true);

        if (inputHandler != null) {
            inputHandler.setGameOver(true);
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> returnToMenu());
        pause.play();
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        if (gameTimer != null) {
            gameTimer.stop();
        }

        gameOverPanel.setVisible(false);

        eventListener.createNewGame();

        gamePanel.requestFocus();
        timeLine.play();
        if (gameTimer != null) {
            gameTimer.start();
        }

        isPause.setValue(false);
        isGameOver.setValue(false);

        if (inputHandler != null) {
            inputHandler.setGameOver(false);
            inputHandler.setPaused(false);
        }

        soundManager.startBackgroundMusic();
    }

    public void showLevelUp(String levelName) {
        timeLine.pause();
        if (gameTimer != null) {
            gameTimer.pause();
        }

        LevelUpNotification notification = new LevelUpNotification(levelName);

        HBox root = (HBox) gamePanel.getScene().getRoot();
        StackPane fullScreenOverlay = new StackPane();
        fullScreenOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85);");
        fullScreenOverlay.setAlignment(Pos.CENTER);

        fullScreenOverlay.prefWidthProperty().bind(root.widthProperty());
        fullScreenOverlay.prefHeightProperty().bind(root.heightProperty());

        fullScreenOverlay.getChildren().add(notification);
        root.getChildren().add(fullScreenOverlay);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> {
            root.getChildren().remove(fullScreenOverlay);

            if (levelName.contains("LAVA")) {
                lavaDisplayManager.setLavaBackground(true);
            } else {
                lavaDisplayManager.setLavaBackground(false);
            }

            if (!levelName.contains("TARGET")) {
                targetChallengeUI.hide();
            }

            timeLine.play();
            if (gameTimer != null) {
                gameTimer.resume();
            }
        });
        pause.play();
    }

    private void togglePause() {
        if (isGameOver.getValue()) {
            return;
        }

        isPause.setValue(!isPause.getValue());

        if (isPause.getValue()) {
            timeLine.pause();
            if (gameTimer != null) {
                gameTimer.pause();
            }
            pauseMenu.setVisible(true);

            if (inputHandler != null) {
                inputHandler.setPaused(true);
            }
        } else {
            resumeGame();
        }
    }

    private void resumeGame() {
        isPause.setValue(false);
        pauseMenu.setVisible(false);
        timeLine.play();
        if (gameTimer != null) {
            gameTimer.resume();
        }
        soundManager.resumeBackgroundMusic();
        gamePanel.requestFocus();

        if (inputHandler != null) {
            inputHandler.setPaused(false);
        }
    }

    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
    }

    private void returnToMenu() {
        if (stage != null && menuController != null) {
            stage.setScene(menuController.getMenuScene());
        }
    }
}