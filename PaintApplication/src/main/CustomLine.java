package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class CustomLine {
    private Line line;
    private Color color;
    public CustomLine(Line line, Color color){
        this.line = line;
        this.color = color;
    }

    public Line getLine() {
        return line;
    }

    public Color getColor() {
        return color;
    }
}
