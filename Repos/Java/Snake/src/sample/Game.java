package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.UI.FieldPanel;
import sample.UI.GamePanel;
import sample.UI.MainMenu;


import static sample.Constant.*;

public class Game extends Application {
    public static FieldPanel fieldPanel;
    public static AnimationTimer gameTimer;
    public static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Game.primaryStage = primaryStage;
        BorderPane gameWindow = createGameWindow();
        Scene scene = createScene(gameWindow);
        primaryStage.sizeToScene();
        loadScene(primaryStage, scene);
        createGameLoop();

    }

    private BorderPane createGameWindow() {
        fieldPanel = new FieldPanel();
        BorderPane root = new BorderPane();
        root.setCenter(new MainMenu(new GamePanel(fieldPanel)));
        return root;
    }

    private Scene createScene(BorderPane gamePane) {
        Scene scene = new Scene(gamePane);
        scene.setOnKeyPressed(event -> fieldPanel.handleInput(event));
        return scene;
    }

    private void loadScene(Stage primaryStage, Scene scene) {
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void createGameLoop() {
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
