package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static sample.Constant.*;

public class Game extends Application {
    public static FieldPanel fieldPanel;
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
        fieldPanel = new FieldPanel();
        BorderPane root = new BorderPane();
        root.setCenter(new GamePanel(fieldPanel));
        return root;
    }

    private Scene createScene(BorderPane gamePane) {
        Scene scene = new Scene(gamePane, GAME_WINDOW_LENGTH + BORDER_THICKNESS * 2, GAME_WINDOW_LENGTH + BORDER_THICKNESS * 2);
        scene.setOnKeyPressed(event -> fieldPanel.handleInput(event));
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
                if(FieldPanel.isSnakeDead()){
                    //Game over
                    System.out.println("Game Over");
                    stop();
                }
                if (Math.abs(currentTime - now) > FPS) {
                    FieldPanel.nextLoop();
                    FieldPanel.validInputEnteredThisFrame = false;
                    currentTime = now;
                }
            }
        };
        gameTimer.start();
    }
}
