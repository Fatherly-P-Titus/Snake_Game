# Snake Game - Java Application Documentation

## Table of Contents
1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [Class Structure](#class-structure)
4. [Game Components](#game-components)
5. [Game Logic](#game-logic)
6. [User Interface](#user-interface)
7. [Controls](#controls)
8. [Features](#features)

## Project Overview

**Developer:** Fatherly P Titus  
**Website:** [https://fatherly-p-titus.github.io/fatherlytitus.github.io/](https://fatherly-p-titus.github.io/fatherlytitus.github.io/)

The Snake Game is a classic arcade-style Java application where players control a snake that grows in length as it consumes food items. The game challenges players to navigate the snake without colliding with walls or itself.

## Architecture

### Design Pattern
The application follows the **Model-View-Controller (MVC)** pattern:
- **Model:** `SnakeGame` class handling game logic and state
- **View:** `GamePanel` class responsible for rendering the game
- **Controller:** Input handling and game flow management

### Main Components
```
src/
├── SnakeGame.java          // Main application entry point
├── GamePanel.java          // Game rendering and display
├── Snake.java             // Snake behavior and movement
├── Food.java              // Food generation and positioning
└── GameState.java         // Game state management
```

## Class Structure

### 1. SnakeGame.java
**Purpose:** Main application class that initializes and runs the game.

**Key Responsibilities:**
- Application entry point (`main` method)
- Game window creation and configuration
- Game loop management
- Component initialization and coordination

**Key Methods:**
- `main(String[] args)` - Application entry point
- `initializeGame()` - Sets up game components
- `startGame()` - Begins the game loop

### 2. GamePanel.java
**Purpose:** Handles game rendering, user interface, and visual feedback.

**Key Responsibilities:**
- Rendering game elements (snake, food, background)
- Displaying game information (score, level, status)
- Handling repaint requests
- Managing visual effects and animations

**Key Methods:**
- `paintComponent(Graphics g)` - Main rendering method
- `drawSnake(Graphics g)` - Renders the snake
- `drawFood(Graphics g)` - Renders food items
- `drawScore(Graphics g)` - Displays score and game info

### 3. Snake.java
**Purpose:** Manages snake behavior, movement, and growth.

**Key Responsibilities:**
- Snake movement and direction control
- Collision detection (self-collision)
- Growth management when food is consumed
- Position tracking

**Key Properties:**
- `List<Point>` body - Snake segments coordinates
- `Direction` currentDirection - Current movement direction
- `int` length - Current snake length

**Key Methods:**
- `move()` - Updates snake position
- `grow()` - Increases snake length
- `checkSelfCollision()` - Detects if snake hits itself
- `changeDirection(Direction newDirection)` - Updates movement direction

### 4. Food.java
**Purpose:** Manages food generation and positioning.

**Key Responsibilities:**
- Random food placement
- Collision-free positioning (not on snake)
- Food type management (if multiple types exist)

**Key Methods:**
- `generateNewPosition()` - Creates new food position
- `isValidPosition(Point position)` - Validates food placement

### 5. GameState.java
**Purpose:** Manages game state and progression.

**Key Responsibilities:**
- Score tracking
- Level management
- Game status (playing, paused, game over)
- High score management

**Key Properties:**
- `int` currentScore
- `int` highScore
- `int` currentLevel
- `boolean` gameRunning
- `boolean` gamePaused

## Game Components

### Game Board
- **Grid-based system** for precise movement
- **Fixed dimensions** (typically 20x20 or 30x30 cells)
- **Coordinate system** for object positioning

### Snake
- **Head:** Leads movement and direction
- **Body:** Follows head in sequence
- **Movement:** Grid-based, one cell per update
- **Growth:** Increases by one segment per food item

### Food
- **Single item** on board at a time
- **Random positioning** within valid locations
- **Consumption effect:** Increases snake length and score

## Game Logic

### Movement System
```java
// Pseudocode for snake movement
public void move() {
    // Save current head position
    Point newHead = calculateNewHeadPosition();
    
    // Add new head to front
    body.add(0, newHead);
    
    // Remove tail if not growing
    if (!isGrowing) {
        body.remove(body.size() - 1);
    }
}
```

### Collision Detection
1. **Wall Collision:** Check if head position exceeds board boundaries
2. **Self Collision:** Check if head position overlaps any body segment
3. **Food Collision:** Check if head position matches food position

### Scoring System
- **Base points** per food item consumed
- **Combo bonuses** for consecutive food consumption
- **Level progression** based on score milestones

## User Interface

### Visual Elements
- **Game Board:** Main playing area with grid background
- **Snake:** Distinct color with head differentiation
- **Food:** Visually distinct from snake and background
- **Score Display:** Current score, high score, and level
- **Game Status:** Playing, paused, or game over messages

### Color Scheme
- **Snake:** Typically green or blue
- **Head:** Different color or shape for visibility
- **Food:** Red or contrasting color
- **Background:** Dark color for contrast
- **UI Elements:** High contrast for readability

## Controls

### Keyboard Input
- **Arrow Keys:** Change snake direction (Up, Down, Left, Right)
- **Space Bar:** Pause/resume game
- **Enter/R:** Restart game after game over
- **ESC:** Exit game

### Input Handling
- **Direction restrictions:** Cannot reverse direction directly
- **Input buffering:** Smooth direction changes
- **Pause toggle:** Instant game state switching

## Features

### Core Features
1. **Progressive Difficulty:** Speed increases with score
2. **Score Tracking:** Real-time score display
3. **High Score Persistence:** Saves best performance
4. **Smooth Controls:** Responsive directional input
5. **Visual Feedback:** Clear game state indicators

### Advanced Features (Potential)
1. **Multiple Food Types:** Different point values
2. **Obstacles:** Additional challenges on higher levels
3. **Power-ups:** Temporary abilities (speed boost, invincibility)
4. **Themes:** Customizable visual styles
5. **Sound Effects:** Audio feedback for actions

## Technical Specifications

### Requirements
- **Java Version:** JDK 8 or higher
- **Memory:** Minimal requirements (typically < 64MB)
- **Display:** Standard resolution support

### Performance
- **Frame Rate:** Consistent 60 FPS target
- **Memory Usage:** Efficient object management
- **Input Response:** Immediate keyboard processing

This documentation provides a comprehensive breakdown of the Snake Game Java application. The modular design ensures maintainability and potential for future enhancements while providing a solid foundation for the classic gaming experience.
