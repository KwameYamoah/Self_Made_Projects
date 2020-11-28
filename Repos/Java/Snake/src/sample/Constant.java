package sample;

import javafx.scene.paint.Color;

public class Constant {
    public static final int GAME_WINDOW_LENGTH = 600;
    public static final int BOARD_LENGTH = 10;
    public static final int CELL_SIZE = GAME_WINDOW_LENGTH /BOARD_LENGTH;
    public static final Color SNAKE_BODY_COLOR = Color.RED;
    public static final Color SNAKE_HEAD_COLOR = Color.GREEN;
    public static final int ONE_SECOND_IN_NANO = 1000000000;
    public static final int FRAMES = 8;
    public static final int FPS = ONE_SECOND_IN_NANO/FRAMES;
    public static final int BORDER_THICKNESS = 5;
    public static final long FOOD_TIMER = 5;




}
