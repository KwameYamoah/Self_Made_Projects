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

public class ModeMenu extends BorderPane {
    private GamePanel gamePanel;
    private Button normalMode = new Button("Normal Mode");
    private Button wrapAroundMode = new Button("Wrap Around Mode");

    public ModeMenu() {
        setPrefSize((double) GAME_WINDOW_LENGTH / 2, (double) GAME_WINDOW_LENGTH / 2);
        styleButtons();
        addEventsToButtons();
        addButtons();
    }

    private void styleButtons() {
        normalMode.setFont(new Font("Arial", 15));
        wrapAroundMode.setFont(new Font("Arial", 15));
        normalMode.setMinWidth(100);
        wrapAroundMode.setMinWidth(100);
    }

    private void addEventsToButtons() {
        normalMode.setOnAction((event) -> {
            getChildren().clear();
            gamePanel = new GamePanel(new FieldPanel(false));
            Game.primaryStage.getScene().setRoot(gamePanel);
            Game.primaryStage.sizeToScene();
            Game.primaryStage.centerOnScreen();
        });

        wrapAroundMode.setOnAction((event -> {
            getChildren().clear();
            gamePanel = new GamePanel(new FieldPanel(true));
            Game.primaryStage.getScene().setRoot(gamePanel);
            Game.primaryStage.sizeToScene();
            Game.primaryStage.centerOnScreen();
            //TODO: set up wrap around parameters
        }));

    }

    private void addButtons() {
        FlowPane buttons = new FlowPane();
        buttons.setVgap(20);
        buttons.getChildren().add(normalMode);
        buttons.getChildren().add(wrapAroundMode);
        buttons.setOrientation(Orientation.VERTICAL);
        buttons.setAlignment(Pos.CENTER);
        setCenter(buttons);
    }
}
