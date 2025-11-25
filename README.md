# TetrisJFX - Enhanced Tetris Game

A modern JavaFX implementation of Tetris with innovative features including a unique Lava Survival mode, comprehensive refactoring, and professional design patterns.

**GitHub Repository:** https://github.com/14Tooba/CW2025

---

##  Compilation Instructions

### Prerequisites
- Java JDK 23
- Maven 3.8.5+
- JavaFX 21.0.6

### Steps to Compile and Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/14Tooba/CW2025.git
   cd CW2025
   ```

2. **Build with Maven:**
   ```bash
   mvn clean install
   ```

3. **Run the game:**
   ```bash
   mvn javafx:run
   ```
   
   Or run `Main.java` directly from your IDE (IntelliJ IDEA recommended)

### Dependencies
- JavaFX Controls 21.0.6
- JavaFX FXML 21.0.6
- JUnit Jupiter 5.12.1 (testing)

---

## ✨ Implemented and Working Properly

### Core Features
- **Classic Tetris Gameplay:** Standard brick movement, rotation, and line clearing
- **Score System:** Real-time score display with quadratic scoring (50 × lines²)
- **Ghost Brick Preview:** Semi-transparent shadow showing landing position (toggle with 'G')
- **Pause Menu:** Clean pause interface with Resume/Quit options (press 'P' or ESC)
- **Sound Effects:** System beep audio for line clears, game over, and rotations

### Innovative Features
- **Main Menu System:** Professional menu with Start, High Scores, and Quit options
- **Two-Level System:**
  - **Level 1 (Classic Mode):** Standard Tetris, advance after clearing 1 line
  - **Level 2 (Lava Survival):** Lava descends from top every 4 seconds, must clear 2 lines to advance
- **Level Transitions:** Epic "LEVEL UP!" notifications with themed visuals
- **Dynamic Backgrounds:** Dark red-tinted background for Lava mode
- **Lava Mechanics:** Continuous bright orange lava that causes game over on contact with blocks

### Controls
- Arrow Keys / WASD: Move and rotate bricks
- P / ESC: Pause/Resume
- G: Toggle ghost brick
- N: New game

---

## Implemented but Not Working Properly

**None** - All implemented features are functioning as expected.

---

## Features Not Implemented

### Planned for Future Updates
- **High Score Persistence:** Save and display best scores across sessions
- **Additional Sound Effects:** Background music and enhanced audio feedback
- **More Game Modes:** Speed Challenge, Puzzle Mode, Marathon Mode
- **Multiplayer Support:** Local split-screen or network play
- **Customizable Controls:** User-defined key bindings
- **Theme System:** Multiple visual themes and color schemes

### Why Not Implemented
These features were planned but not completed due to time constraints and project scope focusing on core gameplay innovation (Lava Survival mode) and code quality (refactoring, testing).

---

## New Java Classes

### Model Layer
- **`LavaManager`** (`model.game`): Manages lava descent, collision detection, and level completion tracking
- **`GameLevel`** (`model.game`): Enum defining game levels (CLASSIC, LAVA_SURVIVAL) with advancement logic
- **`GhostBrickCalculator`** (`model.game`): Calculates landing position for ghost brick preview
- **`LavaBlock`** (`model.game`): Represents individual lava blocks (foundation for lava system)

### View Layer
- **`MainMenu`** (`view`): Main menu screen with styled buttons and navigation
- **`LevelUpNotification`** (`view`): Epic level transition screen with themed visuals for each mode
- **`PauseMenu`** (`view`): In-game pause overlay with resume/quit functionality
- **`GameTimer`** (`view`): Manages automatic brick falling with Timeline
- **`ColorMapper`** (`view`): Utility for mapping brick codes to colors
- **`RectangleRenderer`** (`view`): Helper for styling game rectangles

### Controller Layer
- **`MenuController`** (`controller`): Handles main menu navigation and game launching

### Utility Layer
- **`SoundManager`** (`utils`): Manages game audio using system beep

### Constants
- **`GameConstants`** (`constants`): Centralized configuration for all game values

---

## 🔧 Modified Java Classes

### Major Refactoring

**`SimpleBoard`** (`model.game`)
- Added level tracking system (`currentLevel`, `levelLinesCleared`)
- Integrated `LavaManager` for lava survival mode
- Added methods: `getCurrentLevel()`, `shouldLevelUp()`, `advanceToNextLevel()`, `checkLavaGameOver()`
- Modified `clearRows()` to handle level progression and lava updates
- Updated `newGame()` to reset level state

**`GuiController`** (`view`)
- Added score display binding with `scoreLabel`
- Integrated pause menu functionality
- Added ghost brick rendering system
- Implemented `showLevelUp()` for level transition notifications
- Added `updateLavaDisplay()` for rendering continuous lava
- Added `setLavaBackground()` for dynamic background changes
- Modified `gameOver()` to return to main menu after delay
- Enhanced `refreshBrick()` to update ghost position

**`GameController`** (`controller`)
- Added level-up detection in `onDownEvent()`
- Integrated lava collision checking
- Added board advancement logic on level completion
- Modified constructor to accept `Stage` and `MenuController` for menu integration
- Updated game over flow to support menu return

**`Board`** (interface)
- Added method signatures: `getCurrentLevel()`, `getLavaManager()`, `shouldLevelUp()`, `advanceToNextLevel()`, `checkLavaGameOver()`

**`Main`** (entry point)
- Changed to show `MainMenu` first instead of directly launching game
- Integrated `MenuController` for menu system

**`MatrixOperations`** (`utils`)
- Enhanced with comprehensive JavaDoc comments
- Improved error handling and null checks
- Optimized `checkRemoving()` algorithm

**`Score`** (`model.scoring`)
- No functional changes, maintained original implementation

### Minor Modifications

**`BrickRotator`** (`model.game`)
- Moved to `model.game` package (refactoring)
- Enhanced documentation

**`RandomBrickGenerator`** (`logic.bricks`)
- Refactored to use `BrickFactory` (Factory Pattern)
- Simplified brick generation logic

**`ViewData`**
- Added `ghostPosition` field for ghost brick feature
- Added getter method `getGhostPosition()`

**`GameConstants`** (`constants`)
- Changed `BRICK_SPAWN_Y` from 10 to 0 for proper spawning

**`gameLayout.fxml`**
- Added score label display (VBox with SCORE text and value)
- Fixed transparent backgrounds to prevent black boxes

**`window_style.css`**
- No modifications (kept original styling)

---

## Unexpected Problems

### Issue 1: Game Over Detection Too Early
**Problem:** Game was ending when blocks reached mid-screen instead of the top.

**Cause:** `BRICK_SPAWN_Y` was set to 10 (middle of board) instead of 0 (top).

**Solution:** Changed `GameConstants.BRICK_SPAWN_Y` to 0, allowing proper spawn at the hidden top rows (0-1) while visible area starts at row 2.

### Issue 2: Level Up Text Not Appearing
**Problem:** Level up notification was created but not visible on screen.

**Cause:** Notification was being added to wrong parent pane and not brought to front.

**Solution:** Changed to add notification to scene root and explicitly call `toFront()` to ensure visibility above all game elements.

### Issue 3: Soft Drop Score Inflation
**Problem:** Score increased rapidly when holding down arrow, making gameplay less balanced.

**Cause:** Every manual down movement added +1 point.

**Solution:** Removed soft drop bonus - score now only increases from line clears (50 × lines²).

### Issue 4: Bricks Not Spawning in Lava Mode
**Problem:** After leveling up to Lava mode, no new brick appeared.

**Cause:** `advanceToNextLevel()` cleared the board but didn't create a new brick.

**Solution:** Added `createNewBrick()` call at end of `advanceToNextLevel()` method.

### Issue 5: Grid Visibility in Lava Mode
**Problem:** Dark red background made grid lines and bricks hard to see.

**Cause:** Background was too dark (solid `#1a0a0a`).

**Solution:** Changed to semi-transparent tinted background `rgba(40, 10, 10, 0.6)` allowing grid visibility while maintaining theme.

### Issue 6: JavaFX Scene Root Reuse Error
**Problem:** "MainMenu is already set as root of another scene" error when returning to menu.

**Cause:** Same `MainMenu` instance was being reused for multiple scenes.

**Solution:** Modified `MenuController.getMenuScene()` to create fresh `MainMenu` instance each time.

### Issue 7: Score Display Black Box
**Problem:** Score label had black background rectangle.

**Cause:** Default VBox background was not transparent.

**Solution:** Added `style="-fx-background-color: transparent;"` to score VBox in `gameLayout.fxml`.

---

## Testing

### Test Coverage
- **58 comprehensive unit tests** across all major components
- All tests passing 

### Test Classes
- `MatrixOperationsTest`: 6 tests (collision, merging, row clearing)
- `ScoreTest`: 4 tests (scoring, reset functionality)
- `BrickFactoryTest`: 7 tests (factory pattern, brick creation)
- `ColorMapperTest`: 4 tests (color mapping)
- `GhostBrickCalculatorTest`: 8 tests (landing position calculation)
- `SoundManagerTest`: 6 tests (audio state management)
- `PauseMenuTest`: 5 tests (UI components)
- `LavaManagerTest`: 18 tests (lava mechanics, collision, level completion)

### Running Tests
```bash
mvn test
```

---

##  Architecture & Design Patterns

### Package Structure
```
com.comp2042/
├── constants/          # Configuration values
├── controller/         # Game flow control
├── logic/bricks/       # Brick types and generation
├── model/
│   ├── game/          # Game state and mechanics
│   └── scoring/       # Score management
├── utils/             # Utility classes
└── view/              # UI components
```

### Design Patterns Implemented
- **Factory Pattern:** `BrickFactory` centralizes brick creation
- **State Pattern:** `GameState` and `GameLevel` enums manage game states
- **Single Responsibility Principle:** Extracted `GameTimer`, `ColorMapper`, `RectangleRenderer`, `SoundManager`
- **MVC Architecture:** Clear separation of Model, View, Controller

---

## 🎮 Gameplay Features

### Scoring System
- Single line: 50 points
- Double lines: 200 points (50 × 2²)
- Triple lines: 450 points (50 × 3²)
- Tetris (4 lines): 800 points (50 × 4²)

### Lava Survival Mode
- Triggered after clearing 1 line in Classic mode
- Lava descends from top every 4 seconds
- Continuous bright orange lava fills from top down
- Game over if lava touches any placed block
- Must clear 2 lines to return to Classic mode
- Epic themed transition screen

---

##  Development

### Git Workflow
- Feature branches for all major additions
- Descriptive commit messages
- Regular merges to master

### Key Branches
- `feature/ghost-brick-preview`
- `feature/sound-and-pause`
- `feature/main-menu`
- `feature/score-display`
- `feature/lava-survival-level`
- `fix/game-over-detection`

---

## Notes

This project demonstrates:
- Clean code architecture with proper separation of concerns
- Comprehensive testing methodology
- Professional Git workflow
- Innovative gameplay mechanics (Lava Survival)
- User experience focus (menus, notifications, visual polish)

**Project Status:** Core features complete and functional. Additional features planned for future updates.

**Last Updated:** November 2025

---
