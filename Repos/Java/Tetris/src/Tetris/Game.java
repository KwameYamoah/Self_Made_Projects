package Tetris;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Game extends Application {

    private Pane root;
    private double score;
    private int linesCreated;
    private HashMap<Integer, Cell> board;
    private Pane boardPane;
    public static final int CELL_SIZE = 20;
    public static final int BOARD_WIDTH = 12;
    private static final int BOARD_HEIGHT = 25;
    private static final int SCOREBOARD_HEIGHT = CELL_SIZE * 2;
    private AnimationTimer gameAnimation;
    private static final long ONE_SECOND_IN_NANO = 1000000000;
    private final long FRAMES_PER_SECOND = 2;
    private Pane currentShapePane;
    private TetrisShape currentShape;
    private Text scoreText;

    @Override
    public void start(Stage primaryStage) throws Exception {
        createRoot();
        createGameUI();
        primaryStage.setTitle("Tetris");
        Scene scene = new Scene(root, CELL_SIZE * BOARD_WIDTH, CELL_SIZE * BOARD_HEIGHT + SCOREBOARD_HEIGHT);
        primaryStage.setScene(scene);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case LEFT:
                    moveLeft(currentShape);
                    break;
                case RIGHT:
                    moveRight(currentShape);
                    break;
                case DOWN:
                    moveDown(currentShape);
                    break;
                case Z:
                    rotateLeft();
                    break;
                case C:
                    rotateRight();
                    break;
            }
        });
        createGameLoop();
        primaryStage.show();
    }

    private void createRoot() {
        root = new Pane();
    }

    private void createGameUI() {
        BorderPane gameUI = new BorderPane();
        gameUI.setTop(createScoreBoard());
        gameUI.setBottom(createTetrisBoard());
        root.getChildren().add(gameUI);
    }

    private FlowPane createScoreBoard() {
        FlowPane scoreBoard = new FlowPane();
        scoreBoard.setPrefSize(BOARD_WIDTH * CELL_SIZE, SCOREBOARD_HEIGHT);
        scoreBoard.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        scoreText = new Text();
        scoreBoard.getChildren().add(scoreText);
        return scoreBoard;
    }

    private Pane createTetrisBoard() {
        boardPane = new Pane();
        currentShapePane = new Pane();
        boardPane.getChildren().add(currentShapePane);
        board = new HashMap<>();
        boardPane.setPrefSize(BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                Cell cell = new Cell(x * CELL_SIZE, y * CELL_SIZE, -1);
                if (x == 0 || x == BOARD_WIDTH - 1 || y == BOARD_HEIGHT - 1) {
                    cell.setFill(Color.BLACK);
                    cell.setState(-2);
                } else cell.setFill(Color.WHITE);
                board.put(y * BOARD_WIDTH + x, cell);
                boardPane.getChildren().add(cell);
            }
        }

        boardPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        return boardPane;
    }

    private void createGameLoop() {
        gameAnimation = new AnimationTimer() {
            long startNanoTime = System.nanoTime();

            @Override
            public void handle(long now) {
                if (now - startNanoTime > ONE_SECOND_IN_NANO / FRAMES_PER_SECOND) {
                    startNanoTime = now;
                    applyGameLogic();

                }
                if (now != startNanoTime) {
                    updateScore();
                }
            }
        };
        gameAnimation.start();

    }

    private void applyGameLogic() {
        if (currentShape == null) {
            spawnShape();
        } else {
            moveDown(currentShape);
            score++;
        }
    }

    private boolean moveDown(TetrisShape currentShape) {
        if (currentShape != null) {
            score += 0.5;
            if (!move(currentShape, 0, 0, 1, -1)) {
                this.currentShapePane.getChildren().removeAll();
                addToBoard(currentShape);

                this.currentShape = null;
                checkBoard();
                return false;
            }
        }
        return true;
    }

    private boolean moveLeft(TetrisShape currentShape) {
        return move(currentShape, 1, 0, 0, -1);

    }

    private boolean moveRight(TetrisShape currentShape) {
        return move(currentShape, 0, 1, 0, -1);
    }


    private boolean rotateLeft() {
        System.out.println("Rotate left");
        return move(currentShape, 0, 0, 0, 3);
    }

    private boolean rotateRight() {
        return move(currentShape, 0, 0, 0, 1);
    }


    private boolean move(TetrisShape currentShape, int left, int right, int down, int rotation) {
        if (currentShape == null) return true;
        TetrisShape testShape = new TetrisShape(currentShape);
        testShape.setLocation(currentShape.getX() - left + right, currentShape.getY() + down);
        if (rotation != -1) testShape.rotateShape(rotation);
        if (!checkShapeCollision(testShape)) {
            currentShape.setLocation(currentShape.getX() - left + right, currentShape.getY() + down);
            if (rotation != -1) currentShape.rotateShape(rotation);
            overlayOnBoard(currentShape);

            return true;
        }
        return false;
    }

    private void updateScore() {
        scoreText.setText("Score : " + (int) score);
    }

    private void checkBoard() {
        //board is 0 - 299
        //to check horizontal y * board width + x
        ArrayList<Integer> rowsNeedRemoving = new ArrayList<>();
        for (int y = 0; y < BOARD_HEIGHT - 1; y++) {
            boolean spaceExist = false;
            for (int x = 1; x < BOARD_WIDTH - 1; x++) {
                if (board.get(y * BOARD_WIDTH + x).getState() == -1) {
                    spaceExist = true;
                }

            }
            if (!spaceExist) {
                //clear and shift row downwards
                score += 10;
                rowsNeedRemoving.add(y);
            }
            System.out.println();
        }
        clearLine(rowsNeedRemoving);

    }

    private void clearLine(ArrayList<Integer> rowsToRemove) {
        // need to try using another thread to update board and return board
        for(int row : rowsToRemove) {
            for (int y = row; y >= 0; y--) {
                for (int x = BOARD_WIDTH - 2; x >= 1; x--) {
                    if (y != 0) {
                        board.get(y * BOARD_WIDTH + x).copy(board.get((y * BOARD_WIDTH + x) - BOARD_WIDTH));
                    }
                    else{
                        board.get(y * BOARD_WIDTH + x).reset();
                    }
                }
            }
        }
    }


    private void addToBoard(TetrisShape currentShape) {
        for (Cell cell : currentShape.getCells()) {
            int x = (int) cell.getX() / CELL_SIZE;
            int y = (int) cell.getY() / CELL_SIZE;
            int boardLocation = y * BOARD_WIDTH + x;
            board.get(boardLocation).setState(currentShape.getShapeNumber());
            board.get(boardLocation).setFill(currentShape.getColor());
        }
    }

    private boolean spawnShape() {
        Random random = new Random();
        int shapeNumber = random.nextInt(7);
        TetrisShape tetrisShape = TetrisShape.createShape(shapeNumber, BOARD_WIDTH / 2 - (TetrisShape.GAP + 1), 0);

        if (!checkShapeCollision(tetrisShape)) {
            overlayOnBoard(tetrisShape);
            return true;
        }
        else {
            System.out.println("GameOver");
            gameAnimation.stop();
        }

        return false;
    }

    private void overlayOnBoard(TetrisShape tetrisShape) {
        boardPane.getChildren().remove(currentShapePane);
        currentShapePane = new Pane();
        for (Cell cell : tetrisShape.getCells()) {
            currentShapePane.getChildren().add(cell);
        }
        boardPane.getChildren().add(currentShapePane);
        currentShape = tetrisShape;
    }

    private Boolean checkShapeCollision(TetrisShape shape) {
        TetrisShape testShape = new TetrisShape(shape);

        for (Cell shapePart : testShape.getCells()) {
            if (checkCollision(shapePart)) {

                return true;
            }
        }
        return false;
    }

    private Boolean checkCollision(Cell cell) {
        int x = (int) cell.getX() / CELL_SIZE;
        int y = (int) cell.getY() / CELL_SIZE;

        int boardLocation = y * BOARD_WIDTH + x;

        if (boardLocation > board.size()) return false;
        int state = board.get(boardLocation).getState();
        return state != -1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
