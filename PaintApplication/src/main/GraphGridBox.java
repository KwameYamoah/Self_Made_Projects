package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class GraphGridBox extends Rectangle {
    GraphGridBox(int x, int y, int size) {
        super(size, size, Color.WHITE);
        setStrokeType(StrokeType.INSIDE);
        setStroke(Color.GRAY);
        setStrokeWidth(1);
        setTranslateX(x);
        setTranslateY(y);
    }
}
