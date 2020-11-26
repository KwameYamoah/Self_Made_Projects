package sample.GameObject;

public enum Direction {
    NONE,UP,RIGHT,DOWN,LEFT;

    public static boolean isOpposite(Direction d1, Direction d2) {
        switch (d1){
            case NONE:
                return false;
            case LEFT:
                return d2 == RIGHT;
            case RIGHT:
                return d2 == LEFT;
            case UP:
                return d2 == DOWN;
            case DOWN:
                return d2 == UP;
        }
        return false;
    }
}
