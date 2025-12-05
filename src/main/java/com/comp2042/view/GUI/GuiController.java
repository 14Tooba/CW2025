package com.comp2042.view.GUI;

import com.comp2042.*;
import com.comp2042.Data.DownData;
import com.comp2042.Data.ViewData;
import com.comp2042.controller.GameController;
import com.comp2042.controller.InputEventListener;
import com.comp2042.model.scoring.HighScoreManager;
import com.comp2042.view.*;
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
 *  @see BrickRenderer
 *  @see GameBoardRenderer
 *  @see LavaDisplayManager
 *  @see TargetChallengeUI
 *  @see GameInputHandler
 */
public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    /**
     * Main game panel GridPane for displaying the board background.
     * Injected from FXML layout file.
     */
    @FXML
    private GridPane gamePanel;

    /**
     * Container group for displaying floating notifications.
     * Holds score bonus notifications and other temporary messages.
     */
    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    /**
     * Game over overlay panel displayed when game ends.
     * Shows "GAME OVER" message and transitions back to menu.
     */
    @FXML
    private GameOverPanel gameOverPanel;

    /**
     * Label displaying the current score.
     * Bound to Score model's scoreProperty for automatic updates.
     */
    @FXML
    private Label scoreLabel;

    /**
     * Container for Target Challenge mode UI elements.
     * Shows mission name, remaining blocks, and countdown timer.
     * Visible only during Target Challenge level (Level 3).
     */
    @FXML
    private VBox targetChallengeContainer;

    @FXML
    private Label missionLabel;

    @FXML
    private Label targetBlocksLabel;

    @FXML
    private Label timerLabel;

    // Specialized components
    /**
     * Manages rendering and updates of the static game board background.
     * Handles the display matrix of rectangles representing placed blocks.
     */
    private GameBoardRenderer boardRenderer;

    /**
     * Manages rendering of the current brick and ghost piece.
     * Handles position updates, opacity, and ghost piece toggle.
     */
    private BrickRenderer brickRenderer;

    /**
     * Manages visual display of lava in Lava Survival mode (Level 2).
     * Controls lava row rendering and background atmosphere effects.
     */
    private LavaDisplayManager lavaDisplayManager;

    /**
     * Manages UI display for Target Challenge mode (Level 3).
     * Handles mission info, block counter, and countdown timer updates.
     */
    private TargetChallengeUI targetChallengeUI;

    /**
     * Centralized keyboard input processor for gameplay controls.
     * Handles movement (arrow keys/WASD), rotation, ghost toggle, pause, and new game.
     */
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



    /**
     * Initializes the GUI controller when FXML is loaded.
     * Sets up fonts, audio, pause menu, level-up notification, and Target Challenge UI.
     * Configures initial visibility states and event handlers.
     *
     * @param location The location used to resolve relative paths for the root object, or null
     * @param resources The resources used to localize the root object, or null
     */
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

    /**
     * Sets the primary stage and menu controller references.
     * Must be called after FXML loading to enable scene transitions.
     *
     * @param stage The primary application stage
     * @param menuController The menu controller for navigation
     */
    public void setStageAndMenu(Stage stage, MenuController menuController) {
        this.stage = stage;
        this.menuController = menuController;
    }

    /**
     * Initializes the game view with board matrix and brick data.
     * Creates specialized rendering managers, sets up timelines, and configures keyboard input.
     * Called once when game starts from GameController constructor.
     *
     * @param boardMatrix The initial 2D game board matrix
     * @param brick The initial brick view data with position and shape
     */
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

    /**
     * Handles automatic or user-triggered downward brick movement.
     * Processes line clears, score updates, and brick rendering.
     * Displays score bonus notifications for cleared lines.
     *
     * @param event The move event (USER-triggered or THREAD-triggered)
     */
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

    /**
     * Registers the input event listener (typically GameController).
     * Sets up keyboard input handling and Target Challenge UI controller reference.
     *
     * @param eventListener The listener implementing input event callbacks
     */
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

    /**
     * Executes hard drop - instantly moves brick to ghost piece position.
     * Keeps moving down until reaching the calculated landing position.
     * Plays drop sound effect on completion.
     */
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

    /**
     * Handles rotation input callback from GameInputHandler.
     * Rotates brick counter-clockwise, plays sound, and updates display.
     */
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

    /**
     * Refreshes the game board background with current matrix state.
     * Updates all rectangle colors based on board matrix values.
     *
     * @param board The current 2D game board matrix
     */
    public void refreshGameBackground(int[][] board) {
        boardRenderer.refreshGameBackground(board);
    }

    /**
     * Updates lava visual display for Lava Survival mode (Level 2).
     * Renders lava rows with bright orange color and proper effects.
     *
     * @param lavaRows Array of row indices currently occupied by lava
     */
    public void updateLavaDisplay(int[] lavaRows) {
        lavaDisplayManager.updateLavaDisplay(lavaRows);
    }

    /**
     * Updates Target Challenge display elements (Level 3).
     * Shows mission name, remaining blocks, and formatted time.
     *
     * @param remainingBlocks Number of target blocks still to clear
     * @param formattedTime Time remaining in MM:SS format
     * @param mission Current mission type (Tower, Frame, or Checkerboard)
     */
    public void updateTargetChallengeDisplay(int remainingBlocks, String formattedTime,
                                             com.comp2042.model.game.TargetChallengeManager.MissionType mission) {
        targetChallengeUI.updateDisplay(remainingBlocks, formattedTime, mission);
    }

    /**
     * Triggers game over sequence.
     * Stops timers, plays game over sound, displays game over panel,
     * and transitions back to menu after 3 seconds.
     */
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

    /**
     * Starts a new game session.
     * Resets board, restarts timers, and clears game over state.
     *
     * @param actionEvent The action event (can be null when called programmatically)
     */
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

    /**
     * Displays full-screen level-up notification with themed visuals.
     * Pauses game for 5 seconds while showing notification.
     * Updates lava background and Target Challenge UI visibility based on level.
     *
     * @param levelName The display name of the next level
     */
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

    /**
     * Placeholder pause action handler (currently unused).
     * Focus management is handled through direct method calls.
     *
     * @param actionEvent The action event from pause button
     */
    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
    }

    private void returnToMenu() {
        if (stage != null && menuController != null) {
            stage.setScene(menuController.getMenuScene());
        }
    }
}