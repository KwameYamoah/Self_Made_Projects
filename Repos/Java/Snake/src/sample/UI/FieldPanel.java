package sample.UI;


import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import sample.Game;
import sample.GameObject.Direction;
import sample.GameObject.Snake;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;

import static sample.Constant.*;
import static sample.GameObject.Direction.*;

public class FieldPanel extends Pane {
    //state variables
    public static boolean validInputEnteredThisFrame;
    private static KeyCode previousKey = null;
    public static boolean isWrapAround;
    private Rectangle[][] gameBoard;

    //snake
    private static Snake snake;
    private static boolean snakeDead = false;
    private static ArrayList<Circle> snakeFood;
    private static ArrayList<Circle> timedSnakeFood;
    private static Pane foodPane;

    //labels and data
    private static Label score;
    private static int scorePoints;
    private static final int POINTS_PER_FOOD = 100;
    private static Label label;

    //music player
    private static MediaPlayer bgSound;
    private static MediaPlayer deathSound;

    public FieldPanel(boolean isWrapAround) {
        FieldPanel.isWrapAround = isWrapAround;
        Game.scene.setOnKeyPressed(this::handleInput);
        reset();
    }


    public void reset() {
        clear();
        createBoard();
        createSnake();
        setSnakeDeathPlayer();
        playBackgroundMusic();
        Game.gameTimer.start();

    }


    private void clear() {
        getChildren().clear();
    }

    private void createBoard() {
        addCells(false);
        createPaneAndListToHoldFood();
        validInputEnteredThisFrame = false;
        addLevelDetails();
    }

    public void createSnake() {
        snake = new Snake(this, foodPane);
        snakeDead = false;
        addSnakeToPane();
    }

    private void setSnakeDeathPlayer() {
        Media media = new Media(
                new File("C:\\Users\\Default\\Desktop\\Education\\GitHub\\Self_Made_Projects\\Repos\\Java\\Snake\\src\\sample\\Sounds\\deathEffect.wav").toURI().toString());
        deathSound = new MediaPlayer(media);
        deathSound.setVolume(0.2);
    }


    private static void playPickUpSound() {
        Media media = new Media(
                new File("C:\\Users\\Default\\Desktop\\Education\\GitHub\\Self_Made_Projects\\Repos\\Java\\Snake\\src\\sample\\Sounds\\pickUp.wav").toURI().toString());
        MediaPlayer pickupSound2 = new MediaPlayer(media);
        pickupSound2.setVolume(0.2);
        pickupSound2.play();
    }

    private static void playPickUpSound2() {
        Media media = new Media(
                new File("C:\\Users\\Default\\Desktop\\Education\\GitHub\\Self_Made_Projects\\Repos\\Java\\Snake\\src\\sample\\Sounds\\pickUp2.wav").toURI().toString());
        MediaPlayer pickupSound = new MediaPlayer(media);
        pickupSound.setVolume(0.2);
        pickupSound.play();
    }

    private void playBackgroundMusic() {
        Media media = new Media(
                new File("C:\\Users\\Default\\Desktop\\Education\\GitHub\\Self_Made_Projects\\Repos\\Java\\Snake\\src\\sample\\Sounds\\actionRetroMusic.mp3").toURI().toString());
        bgSound = new MediaPlayer(media);
        bgSound.setCycleCount(MediaPlayer.INDEFINITE);
        bgSound.setAutoPlay(true);
        bgSound.setVolume(0.2);

    }

    private void addLevelDetails() {
        addScore();
        addGameOverLabel();
    }

    private void addScore() {
        scorePoints = 0;
        score = new Label("Score : " + scorePoints);
        score.setLayoutX(CELL_SIZE * BOARD_LENGTH - (CELL_SIZE * 2));
        score.setLayoutY(CELL_SIZE);
        score.setFont(new Font("Arial", 15));
        getChildren().add(score);
    }

    private void addGameOverLabel() {
        label = new Label("GameOver, Your score was " + scorePoints + "\nPress R to retry");
        label.layoutXProperty().bind(widthProperty().subtract(label.widthProperty()).divide(2));
        label.layoutYProperty().bind(heightProperty().subtract(label.heightProperty()).divide(2));
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.PURPLE);
        getChildren().add(label);
        label.setVisible(false);
    }

    private void addCells(boolean showDarkOutlines) {
        gameBoard = new Rectangle[BOARD_LENGTH][BOARD_LENGTH];
        for (int y = 0; y < BOARD_LENGTH; y++) {
            for (int x = 0; x < BOARD_LENGTH; x++) {
                Rectangle rectangle = createBoardCell(showDarkOutlines, y, x);
                gameBoard[y][x] = rectangle;
                getChildren().add(rectangle);
            }
        }
    }

    private Rectangle createBoardCell(boolean showDarkOutlines, int y, int x) {
        Rectangle rectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
        rectangle.setLayoutX(x * CELL_SIZE);
        rectangle.setLayoutY(y * CELL_SIZE);
        rectangle.setFill(Color.WHITE);
        if (showDarkOutlines) {
            rectangle.setStroke(Color.BLACK);
        } else {
            rectangle.setStroke(Color.valueOf("rgb(208, 214, 209)"));
        }
        rectangle.setStrokeType(StrokeType.INSIDE);
        return rectangle;
    }

    private void createPaneAndListToHoldFood() {
        snakeFood = new ArrayList<>();
        timedSnakeFood = new ArrayList<>();
        foodPane = new Pane();
        getChildren().add(foodPane);

    }

    private void addSnakeToPane() {
        for (Snake.BodyPart bodyPart : snake.getWholeBody()) {
            getChildren().add(bodyPart.getRectangle());
        }
    }


    public static void nextLoop() {
        moveSnake();
        createFood();
        validInputEnteredThisFrame = false;
        score.toFront();
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
        if (!checkIfSnakeWillCollide(force, isVertical)) {
            snake.moveBody();
            snake.moveHead(force, isVertical, isWrapAround);
        }

    }

    private static boolean checkIfSnakeWillCollide(int force, boolean isVertical) {
        snake.moveHead(force, isVertical, isWrapAround);
        boolean ifWillCollide = handleSnakeCollisions();
        snake.moveHead(-force, isVertical, isWrapAround);
        return ifWillCollide;
    }

    private static boolean handleSnakeCollisions() {
        Rectangle headCollider = snake.getHead().getRectangle();
        if (outOfField(headCollider)) return true;
        if (snake.getBody() != null && headCollidedWithBody(headCollider)) {
            gameOver();
            return true;
        }

        if (ifHeadCollidesWithFood(headCollider)) {
            addPoints(POINTS_PER_FOOD * 5);
            snake.addBodyPart();
            playPickUpSound();
        }

        if (ifHeadCollidesWithTimedFood(headCollider)) {

            if (snake.getWholeBody().size() == 1) {
                scorePoints = 0;
                score.setText("Score : " + scorePoints);
                gameOver();
            }
            snake.decreaseBodyPart();
            addPoints(POINTS_PER_FOOD * 2);
            playPickUpSound2();
        }

        return false;
    }

    private static boolean outOfField(Rectangle headCollider) {
        if (headCollider.getLayoutX() < 0 || headCollider.getLayoutX() >= GAME_WINDOW_LENGTH ||
                headCollider.getLayoutY() < 0 || headCollider.getLayoutY() >= GAME_WINDOW_LENGTH) {
            gameOver();
            return true;
        }
        return false;
    }

    private static void gameOver() {
        label.setText("GameOver, Your score was " + scorePoints + "\nPress R to retry");
        label.setVisible(true);
        label.toFront();
        bgSound.stop();
        deathSound.play();
        snakeDead = true;
    }

    private static boolean headCollidedWithBody(Rectangle headCollider) {
        for (Snake.BodyPart bodyPart : snake.getBody()) {
            Rectangle collider = bodyPart.getRectangle();
            if (isColliding(headCollider, collider)) {
                return true;
            }
        }
        return false;
    }


    private static boolean ifHeadCollidesWithFood(Rectangle headCollider) {
        Circle foodToRemove = ifFoodFound(headCollider, snakeFood);

        if (foodToRemove != null) {
            snakeFood.remove(foodToRemove);
            return true;
        }
        return false;
    }

    private static boolean ifHeadCollidesWithTimedFood(Rectangle headCollider) {
        Circle foodToRemove = ifFoodFound(headCollider, timedSnakeFood);
        if (foodToRemove != null) {
            timedSnakeFood.remove(foodToRemove);
            return true;
        }
        return false;
    }

    private static Circle ifFoodFound(Rectangle headCollider, ArrayList<Circle> foodList) {
        Circle foodToRemove = null;
        for (Circle food : foodList) {
            Rectangle collider = new Rectangle();
            collider.setLayoutX(food.getLayoutX() - (double) CELL_SIZE / 2);
            collider.setLayoutY(food.getLayoutY() - (double) CELL_SIZE / 2);
            if (isColliding(headCollider, collider)) {
                foodToRemove = food;
                snake.eat(food);
            }
        }
        return foodToRemove;
    }


    private static boolean isColliding(Rectangle headCollider, Rectangle otherCollider) {
        return headCollider.getLayoutX() == otherCollider.getLayoutX() && headCollider.getLayoutY() == otherCollider.getLayoutY();
    }

    private static void addPoints(int points) {
        scorePoints += points;
        score.setText("Score : " + scorePoints);
    }

    public static void createFood() {
        if (snakeFood.isEmpty()) {
            boolean spotIsEmpty = false;
            double x = 0;
            double y = 0;
            int tries = 0;
            while (!spotIsEmpty) {
                x = (int) (Math.random() * BOARD_LENGTH) * CELL_SIZE;
                y = (int) (Math.random() * BOARD_LENGTH) * CELL_SIZE;
                spotIsEmpty = checkIfSpotIsEmpty(x, y);
                tries++;
                if (tries > 100) {
                    break;
                }
            }

            if (spotIsEmpty) {
                createSnakeFoodAt(x, y);
            }
        }
        if (timedSnakeFood.isEmpty()) {
            boolean spotIsEmpty = false;
            double x = 0;
            double y = 0;
            int tries = 0;
            while (!spotIsEmpty) {
                x = (int) (Math.random() * BOARD_LENGTH) * CELL_SIZE;
                y = (int) (Math.random() * BOARD_LENGTH) * CELL_SIZE;
                spotIsEmpty = checkIfSpotIsEmpty(x, y);
                tries++;

                if (tries > 100) {
                    break;
                }
            }

            if (spotIsEmpty) {
                createTimedFood(x, y);
            }
        }
    }

    public static boolean checkIfSpotIsEmpty(double x, double y) {
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

        for (Circle food : timedSnakeFood) {
            Rectangle collider = new Rectangle();
            collider.setLayoutX(food.getLayoutX());
            collider.setLayoutY(food.getLayoutY());
            if (isColliding(spot, collider)) {
                return false;
            }
        }
        return true;
    }

    private static void createSnakeFoodAt(double x, double y) {
        Circle circle = new Circle();
        circle.setLayoutX(x + (double) CELL_SIZE / 2);
        circle.setLayoutY(y + (double) CELL_SIZE / 2);
        circle.setRadius((double) CELL_SIZE / 2);
        circle.setFill(Color.YELLOW);
        foodPane.getChildren().add(circle);
        snakeFood.add(circle);
    }

    public static void createTimedFood(double x, double y) {
        Thread thread = new Thread(() -> {
            Circle circle = new Circle();
            circle.setLayoutX(x + (double) CELL_SIZE / 2);
            circle.setLayoutY(y + (double) CELL_SIZE / 2);
            circle.setRadius((double) CELL_SIZE / 2);
            circle.setFill(Color.PINK);
            Platform.runLater(() -> {
                foodPane.getChildren().add(circle);
                timedSnakeFood.add(circle);
            });

            try {
                Thread.sleep(2500);
                Platform.runLater(() -> {
                    foodPane.getChildren().remove(circle);
                    timedSnakeFood.remove(circle);
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        thread.setDaemon(true);
        thread.start();

    }


    public synchronized void handleInput(KeyEvent event) {
        if (checkIFKeyIsTheSame(event)) return;

        if (!validInputEnteredThisFrame) {
            switch (event.getCode()) {
                case UP:
                    changeHeadDirection(UP);
                    break;
                case RIGHT:
                    changeHeadDirection(RIGHT);
                    break;
                case DOWN:
                    changeHeadDirection(DOWN);
                    break;
                case LEFT:
                    changeHeadDirection(LEFT);
                    break;
                case R:
                    if (snakeDead) {
                        this.reset();
                    }
                    break;
            }

        }
    }

    private boolean checkIFKeyIsTheSame(KeyEvent event) {
        if (event.getCode() != KeyCode.R && event.getCode() != KeyCode.SPACE) {
            if (previousKey != null && previousKey == event.getCode()) {
                return true;
            }
        }
        previousKey = event.getCode();
        return false;
    }

    public void changeHeadDirection(Direction direction) {
        if (!snakeDead) {
            Snake.BodyPart snakeHead = snake.getHead();
            if (!Direction.isOpposite(snakeHead.getDirection(), direction) || (snakeHead.getDirection() == direction)) {
                snakeHead.setDirection(direction);
                validInputEnteredThisFrame = true;
            } else {
                validInputEnteredThisFrame = false;
            }
        }
    }

    public static boolean isSnakeDead() {
        return snakeDead;
    }
}
