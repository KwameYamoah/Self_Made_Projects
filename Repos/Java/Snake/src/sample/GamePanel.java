package sample;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import sample.GameObject.Snake;

import static sample.Constant.*;
import static sample.GameObject.Direction.*;
public class GamePanel extends Pane {
    public static boolean changedThisFrame = false;
    private static KeyCode previousKey = null;
    private Rectangle[][] gameBoard;
    private static Snake snake;
    private static boolean snakeDead = false;

    public GamePanel() {
        createBoard();
        createSnake();
    }

    public void reset(){
        clear();
        createBoard();
        createSnake();
        changedThisFrame = false;
        Game.gameTimer.start();
    }

    private void clear() {
        getChildren().clear();
    }


    public void createBoard() {
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
        snakeDead = false;
    }

    public void createSnake() {
        snake = new Snake(this);
        for (Snake.BodyPart bodyPart : snake.getWholeBody()) {
            getChildren().add(bodyPart.getRectangle());
        }
    }

    public static void move(int force, boolean isVertical) {
        moveBody();
        updateDirection(force, isVertical);
    }

    private static void moveBody() {
        Snake.BodyPart previous = snake.getHead();
        if (snake.getBody() != null) {
            for (Snake.BodyPart bodyPart : snake.getBody()) {
                previous = bodyPart.moveTo(previous);
            }
        }
    }

    private static void updateDirection(int force, boolean isVertical) {
        Rectangle head = snake.getHead().getRectangle();
        if (isVertical) {
            head.setLayoutY(head.getLayoutY() + force);
            if ((force > 0)) {
                snake.getHead().setDirection(DOWN);
            } else {
                snake.getHead().setDirection(UP);
            }
        } else {
            head.setLayoutX(head.getLayoutX() + force);
            if ((force > 0)) {
                snake.getHead().setDirection(RIGHT);
            }
            else {
                snake.getHead().setDirection(LEFT);
            }
        }
    }

    public static void nextLoop() {
        checkCollision();
        moveSnakeHead();
    }

    public static boolean checkCollision() {
        Rectangle headCollider = snake.getHead().getRectangle();
        System.out.println(headCollider.getLayoutX());
        if(headCollider.getLayoutX() < 0 || headCollider.getLayoutX() >= GAME_WINDOW_LENGTH ||
                headCollider.getLayoutY() < 0 || headCollider.getLayoutY() >= GAME_WINDOW_LENGTH){
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
        }
        return false;
    }

    private static boolean isColliding(Rectangle headCollider, Rectangle otherCollider) {
        System.out.println(headCollider.getLayoutX());

        return headCollider.getLayoutX() == otherCollider.getLayoutX() && headCollider.getLayoutY() == otherCollider.getLayoutY();
    }

    private static void moveSnakeHead() {
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

    public synchronized  void handleInput(KeyEvent event) {
        if(event.getCode()!=KeyCode.R && event.getCode()!=KeyCode.SPACE){
            if (previousKey != null && previousKey == event.getCode()) {
                return;
            }
        }
        previousKey = event.getCode();

        if (!changedThisFrame) {
            switch (event.getCode()) {
                case UP:
                    if (snake.getHead().getDirection() != DOWN) snake.getHead().setDirection(UP);
                    else changedThisFrame = false;
                    break;
                case RIGHT:
                    if (snake.getHead().getDirection() != LEFT) snake.getHead().setDirection(RIGHT);
                    else changedThisFrame = false;
                    break;
                case DOWN:
                    if (snake.getHead().getDirection() != UP) snake.getHead().setDirection(DOWN);
                    else changedThisFrame = false;
                    break;
                case LEFT:
                    if (snake.getHead().getDirection() != RIGHT) snake.getHead().setDirection(LEFT);
                    else changedThisFrame = false;
                    break;
                case R:
                    this.reset();
                    break;
                case SPACE:
                    snake.addBodyPart();
                    changedThisFrame = false;
                    break;
            }
            changedThisFrame = true;
        }
    }



    public static boolean isSnakeDead() {
        return snakeDead;
    }


}
