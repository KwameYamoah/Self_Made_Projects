package Tetris;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

import static Tetris.Game.CELL_SIZE;

public class TetrisShape {
    private String shape;
    private final int shapeNumber;
    private int x;
    private int y;
    private Color color;
    static final int GAP = 1;



    TetrisShape(int shapeNumber, int x, int y) {
        this.shape = shapes[shapeNumber];
        this.shapeNumber = shapeNumber;
        this.x = x;
        this.y = y;
        Random rand = new Random();
        int red = rand.nextInt(2);
        int blue = rand.nextInt(2);
        int green = rand.nextInt(2);
        if (red == 1 && blue == 1 && green == 1) {
            red = 0;
        }

        if (red == 0 && blue == 0 && green == 0) {
            red = 1;
        }
        color = new Color(red, green, blue, 1);

    }



    TetrisShape(TetrisShape targetShape) {
        this.shape = targetShape.shape;
        this.shapeNumber = targetShape.shapeNumber;
        this.x = targetShape.x;
        this.y = targetShape.y;
        this.color = targetShape.color;

    }

    private final String shape1 = ".X.." +
                                ".X.." +
                                ".X.." +
                                ".X..";

    private final String shape2 = ".XX." +
                                    ".X.." +
                                    ".X.." +
                                    "....";

    private final String shape3 = "...." +
                                    ".XX." +
                                    ".XX." +
                                    "....";

    private final String shape4 = "..X." +
            ".XX." +
            "..X." +
            "....";

    private final String shape5 = ".X.." +
            ".XX." +
            "..X." +
            "....";

    private final String shape6 = "..X." +
            ".XX." +
            ".X.." +
            "....";

    private final String shape7 = ".XX." +
            "..X." +
            "..X." +
            "....";

    private final String[] shapes = new String[]{shape1, shape2, shape3, shape4, shape5, shape6, shape7};

    static TetrisShape createShape(int shapeNumber, int x, int y) {
        return new TetrisShape(shapeNumber % 7, x, y);
    }


    void rotateShape(int rotation) {
        char[] rotateString = new char[16];
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                int pi = y * 4 + x;
                int ri = getRotation(x, y, rotation);
                rotateString[pi] = shape.charAt(ri);
            }
        }
        shape = charToString(rotateString);
    }

    private static String charToString(char[] chars) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch : chars) {
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    ArrayList<Cell> getCells() {
        ArrayList<Cell> cells = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            if (shape.charAt(i) == 'X') {
                Cell cell = new Cell((CELL_SIZE * x) + (CELL_SIZE * (i % 4)), (CELL_SIZE * y) + CELL_SIZE * (i / 4), shapeNumber);
                cell.setFill(color);
                cells.add(cell);
            }
        }
        return cells;
    }

    public int getShapeNumber() {
        return shapeNumber;
    }

    public Color getColor() {
        return color;
    }

    private static int getRotation(int x, int y, int rotation) {
        switch (rotation % 4) {
            case 0:
                return y * 4 + x;
            case 1:
                return (12 + y) - (x * 4);
            case 2:
                return (15 - (y * 4)) - x;
            case 3:
                return (3 - y) + (x * 4);
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String firstLine = shape.charAt(0) + " " + shape.charAt(1) + " " + shape.charAt(2) + " " + shape.charAt(3) + "\n";
        String secondLine = shape.charAt(4) + " " + shape.charAt(5) + " " + shape.charAt(6) + " " + shape.charAt(7) + "\n";
        String thirdLine = shape.charAt(8) + " " + shape.charAt(9) + " " + shape.charAt(10) + " " + shape.charAt(11) + "\n";
        String fourthLine = shape.charAt(12) + " " + shape.charAt(13) + " " + shape.charAt(14) + " " + shape.charAt(15) + "\n";
        sb.append(firstLine);
        sb.append(secondLine);
        sb.append(thirdLine);
        sb.append(fourthLine);
        return sb.toString();
    }


}
