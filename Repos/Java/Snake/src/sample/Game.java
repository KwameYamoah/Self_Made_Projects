package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.UI.FieldPanel;
import sample.UI.MainMenu;


import static sample.Constant.*;

public class Game extends Application {
    public static AnimationTimer gameTimer;
    public static Stage primaryStage;
    public static Scene scene;
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
        BorderPane root = new BorderPane();
        root.setCenter(new MainMenu());
        return root;
    }

    private Scene createScene(BorderPane gamePane) {
        scene = new Scene(gamePane);
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
                if (Math.abs(currentTime - now) > FPS) {
                    FieldPanel.nextLoop();
                    currentTime = now;
                    if(FieldPanel.isSnakeDead()){
                        System.out.println("Setting back to false 3");
                        FieldPanel.validInputEnteredThisFrame = false;
                        stop();
                    }
                }



            }
        };
    }
}
