package sample.UI;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import static sample.Constant.*;

public class GamePanel extends BorderPane {
    public GamePanel(FieldPanel fieldPanel) {
        if (!FieldPanel.isWrapAround) {
            Rectangle border = new Rectangle(CELL_SIZE * BOARD_LENGTH + BORDER_THICKNESS * 2, CELL_SIZE * BOARD_LENGTH + BORDER_THICKNESS * 2);
            border.setFill(Color.WHITE);
            border.setStroke(Color.BLACK);
            border.setStrokeType(StrokeType.INSIDE);
            border.setStrokeWidth(BORDER_THICKNESS);
            getChildren().add(border);
            setPadding(new Insets(BORDER_THICKNESS));
        }
        setCenter(fieldPanel);
    }
}
