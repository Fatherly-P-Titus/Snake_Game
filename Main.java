package SnakeGame;
// SnakeGame.java
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main  extends Application {
    
    // Game constants
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int CELL_SIZE = 20;
    private static final int GRID_WIDTH = WIDTH / CELL_SIZE;
    private static final int GRID_HEIGHT = HEIGHT / CELL_SIZE;
    
    // Game objects
    private List<Point> snake;
    private Point food;
    private Direction direction;
    private boolean gameRunning;
    private int score;
    
    // JavaFX components
    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    
    private Random random = new Random();
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.BLACK);
        
        // Handle keyboard input
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.UP && direction != Direction.DOWN) {
                direction = Direction.UP;
            } else if (key == KeyCode.DOWN && direction != Direction.UP) {
                direction = Direction.DOWN;
            } else if (key == KeyCode.LEFT && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            } else if (key == KeyCode.RIGHT && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            } else if (key == KeyCode.SPACE && !gameRunning) {
                startGame();
            }
        });
        
        primaryStage.setTitle("Snake Game - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        initializeGame();
        drawStartScreen();
    }
    
    private void initializeGame() {
        snake = new ArrayList<>();
        direction = Direction.RIGHT;
        gameRunning = false;
        score = 0;
        
        // Create game loop
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000) { // 100ms update rate
                    if (gameRunning) {
                        updateGame();
                        drawGame();
                    }
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }
    
    private void startGame() {
        // Initialize snake with 3 segments
        snake.clear();
        snake.add(new Point(GRID_WIDTH / 2, GRID_HEIGHT / 2));
        snake.add(new Point(GRID_WIDTH / 2 - 1, GRID_HEIGHT / 2));
        snake.add(new Point(GRID_WIDTH / 2 - 2, GRID_HEIGHT / 2));
        
        direction = Direction.RIGHT;
        spawnFood();
        gameRunning = true;
        score = 0;
    }
    
    private void updateGame() {
        if (!gameRunning) return;
        
        // Move snake
        Point head = snake.get(0);
        Point newHead = new Point(head.x, head.y);
        
        switch (direction) {
            case UP -> newHead.y--;
            case DOWN -> newHead.y++;
            case LEFT -> newHead.x--;
            case RIGHT -> newHead.x++;
        }
        
        // Check wall collision
        if (newHead.x < 0 || newHead.x >= GRID_WIDTH || 
            newHead.y < 0 || newHead.y >= GRID_HEIGHT) {
            gameOver();
            return;
        }
        
        // Check self collision
        for (Point segment : snake) {
            if (newHead.equals(segment)) {
                gameOver();
                return;
            }
        }
        
        // Add new head
        snake.add(0, newHead);
        
        // Check food collision
        if (newHead.equals(food)) {
            score += 10;
            spawnFood();
        } else {
            // Remove tail if no food eaten
            snake.remove(snake.size() - 1);
        }
    }
    
    private void spawnFood() {
        while (true) {
            int x = random.nextInt(GRID_WIDTH);
            int y = random.nextInt(GRID_HEIGHT);
            food = new Point(x, y);
            
            // Make sure food doesn't spawn on snake
            boolean onSnake = false;
            for (Point segment : snake) {
                if (segment.equals(food)) {
                    onSnake = true;
                    break;
                }
            }
            
            if (!onSnake) break;
        }
    }
    
    private void drawStartScreen() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 36));
        gc.fillText("SNAKE GAME", WIDTH / 2 - 100, HEIGHT / 2 - 50);
        
        gc.setFont(Font.font("Arial", 18));
        gc.fillText("Press SPACE to Start", WIDTH / 2 - 80, HEIGHT / 2 + 20);
        gc.fillText("Use Arrow Keys to Control", WIDTH / 2 - 90, HEIGHT / 2 + 50);
    }
    
    private void drawGame() {
        // Clear canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        
        // Draw food
        gc.setFill(Color.RED);
        gc.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        
        // Draw snake
        for (int i = 0; i < snake.size(); i++) {
            Point segment = snake.get(i);
            
            // Head is different color
            if (i == 0) {
                gc.setFill(Color.LIMEGREEN);
            } else {
                gc.setFill(Color.GREEN);
            }
            
            gc.fillRect(segment.x * CELL_SIZE, segment.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            
            // Draw border around each segment
            gc.setStroke(Color.DARKGREEN);
            gc.strokeRect(segment.x * CELL_SIZE, segment.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        
        // Draw score
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 20));
        gc.fillText("Score: " + score, 10, 30);
    }
    
    private void drawGameOver() {
        // Draw semi-transparent overlay
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 36));
        gc.fillText("GAME OVER", WIDTH / 2 - 100, HEIGHT / 2 - 50);
        
        gc.setFont(Font.font("Arial", 24));
        gc.fillText("Score: " + score, WIDTH / 2 - 50, HEIGHT / 2);
        
        gc.setFont(Font.font("Arial", 18));
        gc.fillText("Press SPACE to Restart", WIDTH / 2 - 90, HEIGHT / 2 + 50);
    }
    
    private void gameOver() {
        gameRunning = false;
        drawGameOver();
    }
}