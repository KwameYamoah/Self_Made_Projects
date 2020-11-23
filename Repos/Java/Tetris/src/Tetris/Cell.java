package Tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static Tetris.Game.CELL_SIZE;

public class Cell extends Rectangle {
    private int state;

    Cell(int x, int y, int state) {
        super(x, y, CELL_SIZE, CELL_SIZE);
        setStroke(Color.GREY);
        this.state = state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void copy(Cell cell){
        this.state = cell.state;
        setFill(cell.getFill());

    }

    public void reset() {
        state = -1;
        setFill(Color.WHITE);
    }
}

