# TetrisJFX - Enhanced Tetris Game

A modern JavaFX implementation of the classic Tetris game with enhanced features, improved architecture, and comprehensive testing.

Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [How to Play](#how-to-play)
- [Refactoring & Design Patterns](#refactoring--design-patterns)
- [Testing](#testing)
- [Technologies Used](#technologies-used)
- [Development Process](#development-process)

---

Features

### Core Gameplay
- Classic Tetris mechanics with 7 different brick types (I, J, L, O, S, T, Z)
- Smooth brick rotation and movement
- Line clearing with score calculation
- Progressive difficulty
- Game over detection

### Enhanced Features
- Ghost Brick Preview: Semi-transparent shadow showing where the brick will land
- Sound System: Audio feedback for line clears, game over, and brick rotation
- Pause Menu: Clean pause interface with Resume and Quit options
- Flexible Controls: Arrow keys or WASD for movement, P/ESC for pause, G to toggle ghost

### Quality of Life Improvements
- Next brick preview
- Real-time score display
- Smooth animations and visual effects
- Responsive keyboard controls

---

## Project Structure

```
src/
├── main/
│   ├── java/com/comp2042/
│   │   ├── constants/          # Game constants and configuration
│   │   │   └── GameConstants.java
│   │   ├── controller/         # Game flow controllers
│   │   │   ├── GameController.java
│   │   │   └── InputEventListener.java
│   │   ├── logic/
│   │   │   └── bricks/         # Brick types and generation
│   │   │       ├── Brick.java
│   │   │       ├── BrickFactory.java
│   │   │       ├── BrickGenerator.java
│   │   │       ├── RandomBrickGenerator.java
│   │   │       └── [I,J,L,O,S,T,Z]Brick.java
│   │   ├── model/
│   │   │   ├── game/           # Game logic and state
│   │   │   │   ├── Board.java
│   │   │   │   ├── SimpleBoard.java
│   │   │   │   ├── BrickRotator.java
│   │   │   │   ├── GameState.java
│   │   │   │   └── GhostBrickCalculator.java
│   │   │   └── scoring/        # Score management
│   │   │       └── Score.java
│   │   ├── utils/              # Utility classes
│   │   │   ├── MatrixOperations.java
│   │   │   └── SoundManager.java
│   │   ├── view/               # UI components
│   │   │   ├── GuiController.java
│   │   │   ├── GameOverPanel.java
│   │   │   ├── NotificationPanel.java
│   │   │   ├── PauseMenu.java
│   │   │   ├── GameTimer.java
│   │   │   ├── ColorMapper.java
│   │   │   └── RectangleRenderer.java
│   │   ├── Main.java
│   │   └── [Data Transfer Objects]
│   └── resources/
│       ├── gameLayout.fxml
│       ├── window_style.css
│       └── digital.ttf
└── test/
    └── java/com/comp2042/        # Comprehensive unit tests
        ├── utils/
        ├── model/
        ├── logic/
        └── view/
```

---

## Installation & Setup

### Prerequisites
- Java JDK 23 or higher
- Maven, already provided
- JavaFX 21.0.6

### Steps
1. **Clone the repository**
   ```bash
   git clone https://github.com/14Tooba/CW2025.git
   cd CW2025
   ```

2. **Build with Maven**
   ```bash
   mvn clean help run
   ```

3. **Run the game**
   ```bash
   mvn javafx:run
   ```

---

## How to Play

### Controls
| Key | Action |
|-----|--------|
| ⬅️ / A | Move brick left |
| ➡️ / D | Move brick right |
| ⬇️ / S | Soft drop (move down faster) |
| ⬆️ / W | Rotate brick |
| G | Toggle ghost brick preview |
| P / ESC | Pause/Resume game |
| N | New game |

### Scoring
- **Single line clear**: 50 points
- **Multiple lines**: Score scales quadratically (50 × lines²)
    - 2 lines = 200 points
    - 3 lines = 450 points
    - 4 lines (Tetris!) = 800 points
- **Soft drop**: +1 point per cell

### Objective
- Clear horizontal lines by filling them completely with bricks
- Game ends when bricks stack to the top
- Achieve the highest score possible!

---

## Refactoring & Design Patterns

### Major Refactoring Achievements

#### 1. **Package Reorganization**
Restructured codebase following MVC-inspired architecture:
- `model`: Game logic and state management
- `view`: UI components and rendering
- `controller`: Input handling and game flow
- `utils`: Reusable utility functions
- `constants`: Centralized configuration

#### 2. **Design Patterns Implemented**

**Factory Pattern** (`BrickFactory`)
- Centralizes brick creation logic
- Easy to add new brick types
- Eliminates code duplication

```java
Brick brick = BrickFactory.createBrick(BrickType.I_BRICK);
```

**State Pattern** (`GameState`)
- Clean game state management
- States: PLAYING, PAUSED, GAME_OVER, READY
- Prevents invalid state transitions

**Single Responsibility Principle**
- Extracted specialized classes:
    - `GameTimer`: Handles automatic brick dropping
    - `ColorMapper`: Maps brick colors to visual representation
    - `RectangleRenderer`: Manages rectangle styling
    - `SoundManager`: Handles all audio functionality
    - `GhostBrickCalculator`: Calculates ghost brick landing position

#### 3. **Code Quality Improvements**
- **Constants Extraction**: All magic numbers moved to `GameConstants`
- **Encapsulation**: All fields properly private with controlled access
- **Documentation**: Comprehensive JavaDoc comments
- **Error Handling**: Robust null checks and exception handling

---

## Testing

### Test Coverage
- **40 comprehensive unit tests** covering core functionality
- Tests organized by component

### Test Suites

| Component | Tests | Purpose |
|-----------|-------|---------|
| MatrixOperations | 6 | Collision detection, matrix operations, row clearing |
| Score | 4 | Score calculation and reset functionality |
| BrickFactory | 7 | Brick creation and type validation |
| ColorMapper | 4 | Color mapping correctness |
| GhostBrickCalculator | 8 | Landing position calculation |
| SoundManager | 6 | Sound state management |
| PauseMenu | 5 | UI component initialization |

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=MatrixOperationsTest
```

### Test Results
All 40 tests passing  
Comprehensive edge case coverage

---

## Technologies Used

- **Java 23**: Core programming language
- **JavaFX 21.0.6**: UI framework
- **Maven**: Build automation and dependency management
- **JUnit 5.12.1**: Unit testing framework
- **Git**: Version control

---

## Development Process

### Git Workflow
Followed professional Git practices with feature branches:

```
master
  ├── refactor/package-structure
  ├── refactor/single-responsibility
  ├── test/refactored-classes
  ├── feature/ghost-brick-preview
  └── feature/sound-and-pause
```

### Commit History Highlights
- Initial package reorganization and constants extraction
- Implementation of Factory and State design patterns
- Comprehensive unit test suite
- Ghost brick preview feature
- Sound system and pause menu

### Key Commits
1. **Refactoring Phase**
    - Package reorganization
    - Design pattern implementation
    - Single responsibility extractions

2. **Feature Addition Phase**
    - Ghost brick preview
    - Sound system
    - Enhanced pause menu

3. **Testing Phase**
    - Unit tests for all core components
    - Edge case coverage
    - Integration testing

---

## Future Enhancements

Planned features for future development:
- Multiple game modes (Lava Fall, Speed Challenge, Mission Mode)
-  High score persistence
-  Main menu with level selection
- Additional sound effects and background music
-  Customizable themes and skins
-  Multiplayer support
-  Mobile touch controls

---


**Tooba**  
GitHub: [@14Tooba](https://github.com/14Tooba)


---

*Last Updated: November 17, 2025*
