package sample;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import sample.GameObject.Snake;

import java.util.ArrayList;

import static sample.Constant.*;
import static sample.GameObject.Direction.*;
public class GamePanel extends Pane {
    public static boolean validInputEnteredThisFrame = false;
    private static KeyCode previousKey = null;
    private Rectangle[][] gameBoard;
    private static Snake snake;
    private static boolean snakeDead = false;
    private static ArrayList<Circle> snakeFood;
    private static Pane foodPane;
    public GamePanel() {
        createBoard();
        createSnake();
    }

    public void reset(){
        clear();
        createBoard();
        createSnake();
        Game.gameTimer.start();
    }

    private void clear() {
        getChildren().clear();
    }

    private void createBoard() {
        addCells();
        createPaneAndListToHoldFood();
        validInputEnteredThisFrame = false;
    }

    private void addCells() {
        gameBoard = new Rectangle[BOARD_LENGTH][BOARD_LENGTH];
        for (int y = 0; y < BOARD_LENGTH; y++) {
            for (int x = 0; x < BOARD_LENGTH; x++) {
                Rectangle rectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
                rectangle.setLayoutX(x * CELL_SIZE);
                rectangle.setLayoutY(y * CELL_SIZE);
                rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeType(StrokeType.INSIDE);
                gameBoard[y][x] = rectangle;
                getChildren().add(rectangle);
            }
        }
    }

    private void createPaneAndListToHoldFood() {
        foodPane = new Pane();
        getChildren().add(foodPane);
        snakeFood = new ArrayList<>();
    }

    public void createSnake() {
        snake = new Snake(this, foodPane);
        snakeDead = false;
        addSnakeToPane();
    }

    private void addSnakeToPane() {
        for (Snake.BodyPart bodyPart : snake.getWholeBody()) {
            getChildren().add(bodyPart.getRectangle());
        }
    }


    public static void nextLoop() {
        moveSnake();
        checkCollision();
    }

    private static void moveSnake() {
        switch (snake.getHead().getDirection()) {
            case UP:
                move(-CELL_SIZE, true);
                break;
            case RIGHT:
                move(CELL_SIZE, false);
                break;
            case DOWN:
                move(CELL_SIZE, true);
                break;
            case LEFT:
                move(-CELL_SIZE, false);
                break;
        }
    }

    private static void move(int force, boolean isVertical) {
        snake.moveBody();
        snake.moveHead(force, isVertical);
    }

    private static boolean checkCollision() {
        Rectangle headCollider = snake.getHead().getRectangle();

        if (headCollider.getLayoutX() < 0 || headCollider.getLayoutX() >= GAME_WINDOW_LENGTH ||
                headCollider.getLayoutY() < 0 || headCollider.getLayoutY() >= GAME_WINDOW_LENGTH) {
            snakeDead = true;
            return true;
        }

        if (snake.getBody() != null) {
            for (Snake.BodyPart bodyPart : snake.getBody()) {
                Rectangle collider = bodyPart.getRectangle();
                if (isColliding(headCollider, collider)) {
                    snakeDead = true;
                    return true;
                }
            }
            boolean foodFound = false;
            Circle foodToRemove = null;
            for (Circle food : snakeFood) {
                Rectangle collider = new Rectangle();
                collider.setLayoutX(food.getLayoutX() - (double)CELL_SIZE/2);
                collider.setLayoutY(food.getLayoutY()  - (double)CELL_SIZE/2);
                if (isColliding(headCollider, collider)) {
                    foodFound = true;
                    foodToRemove = food;
                    snake.eat(food);
                }
            }

            if(foodFound) snakeFood.remove(foodToRemove);
        }
        return false;
    }

    private static boolean isColliding(Rectangle headCollider, Rectangle otherCollider) {
        return headCollider.getLayoutX() == otherCollider.getLayoutX() && headCollider.getLayoutY() == otherCollider.getLayoutY();
    }



    public synchronized  void handleInput(KeyEvent event) {
        if(event.getCode()!=KeyCode.R && event.getCode()!=KeyCode.SPACE){
            if (previousKey != null && previousKey == event.getCode()) {
                return;
            }
        }
        previousKey = event.getCode();

        if (!validInputEnteredThisFrame) {
            switch (event.getCode()) {
                case UP:
                    if (snake.getHead().getDirection() != DOWN) snake.getHead().setDirection(UP);
                    else validInputEnteredThisFrame = false;
                    break;
                case RIGHT:
                    if (snake.getHead().getDirection() != LEFT) snake.getHead().setDirection(RIGHT);
                    else validInputEnteredThisFrame = false;
                    break;
                case DOWN:
                    if (snake.getHead().getDirection() != UP) snake.getHead().setDirection(DOWN);
                    else validInputEnteredThisFrame = false;
                    break;
                case LEFT:
                    createFood();
                    if (snake.getHead().getDirection() != RIGHT) snake.getHead().setDirection(LEFT);
                    else validInputEnteredThisFrame = false;
                    break;
                case R:
                    this.reset();
                    break;
                case SPACE:
                    snake.addBodyPart();

                    validInputEnteredThisFrame = false;
                    break;
            }
            validInputEnteredThisFrame = true;
        }
    }

    public void createFood(){
        boolean spotIsEmpty =  false;
        double x = 0;
        double y = 0;
        int tries = 0;
        while(!spotIsEmpty){
             x = (int)(Math.random() * BOARD_LENGTH) * CELL_SIZE;
             y = (int)(Math.random() * BOARD_LENGTH) * CELL_SIZE;
             spotIsEmpty = checkIfSpotIsEmpty(x,y);
             tries++;
            if(tries > 1000){
                break;
            }
        }

        if(spotIsEmpty){
            Circle circle = new Circle();
            circle.setLayoutX(x + (double)CELL_SIZE/2);
            circle.setLayoutY(y + (double)CELL_SIZE/2);
            circle.setRadius((double)CELL_SIZE/2);
            circle.setFill(Color.YELLOW);
            foodPane.getChildren().add(circle);
            snakeFood.add(circle);

        }
    }

    public boolean checkIfSpotIsEmpty(double x, double y){
        Rectangle spot = new Rectangle();
        spot.setLayoutX(x);
        spot.setLayoutY(y);
        for (Snake.BodyPart bodyPart : snake.getWholeBody()) {
            Rectangle collider = bodyPart.getRectangle();
            if (isColliding(spot, collider)) {
                return false;
            }
        }

        for (Circle food : snakeFood) {
            Rectangle collider = new Rectangle();
            collider.setLayoutX(food.getLayoutX());
            collider.setLayoutY(food.getLayoutY());
            if (isColliding(spot, collider)) {
                return false;
            }
        }
        return true;
    }


    public static boolean isSnakeDead() {
        return snakeDead;
    }
}