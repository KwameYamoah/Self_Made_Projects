package sample.UI;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import sample.Game;

import static sample.Constant.GAME_WINDOW_LENGTH;

public class MainMenu extends BorderPane {
    private GamePanel gamePanel;
    private Button start = new Button("Start");
    private Button exit = new Button("Exit");

    public MainMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setPrefSize((double)GAME_WINDOW_LENGTH/2, (double)GAME_WINDOW_LENGTH/2);
        styleButtons();
        addEventsToButtons();
        addButtons();

    }


    private void styleButtons() {
        start.setFont(new Font("Arial", 15));
        exit.setFont(new Font("Arial", 15));
        start.setMinWidth(100);
        exit.setMinWidth(100);
    }
    private void addEventsToButtons() {
        start.setOnAction((event)->{
            getChildren().clear();
            Game.primaryStage.getScene().setRoot(gamePanel);
            Game.primaryStage.sizeToScene();
            Game.primaryStage.centerOnScreen();
        });

        exit.setOnAction((event -> {
            Platform.exit();
        }));

    }

    private void addButtons() {
        FlowPane buttons = new FlowPane();
        buttons.setVgap(20);
        buttons.getChildren().add(start);
        buttons.getChildren().add(exit);
        buttons.setOrientation(Orientation.VERTICAL);
        buttons.setAlignment(Pos.CENTER);
        setCenter(buttons);
    }
}
