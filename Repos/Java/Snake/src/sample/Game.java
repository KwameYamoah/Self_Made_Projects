package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static sample.Constant.*;

public class Game extends Application {
    public static GamePanel gamePanel;
    public static AnimationTimer gameTimer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane gamePane = createGamePane();
        Scene scene = createScene(gamePane);
        loadScene(primaryStage, scene);
        createGameLoop();
    }

    private BorderPane createGamePane() {
        gamePanel = new GamePanel();
        BorderPane root = new BorderPane();
        root.setCenter(gamePanel);
        return root;
    }

    private Scene createScene(BorderPane gamePane) {
        Scene scene = new Scene(gamePane, GAME_WINDOW_LENGTH, GAME_WINDOW_LENGTH);
        scene.setOnKeyPressed(event -> gamePanel.handleInput(event));
        return scene;
    }

    private void loadScene(Stage primaryStage, Scene scene) {
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            long currentTime = 0;
            @Override
            public void handle(long now) {
                if(GamePanel.isSnakeDead()){
                    //Game over
                    System.out.println("Game Over");
                    stop();
                }
                if (Math.abs(currentTime - now) > FPS) {
                    GamePanel.nextLoop();
                    GamePanel.validInputEnteredThisFrame = false;
                    currentTime = now;
                }
            }
        };
        gameTimer.start();
    }
}
