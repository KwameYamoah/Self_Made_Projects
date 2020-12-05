package sample.GameObject;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.paint.Color;
import sample.UI.FieldPanel;

import java.util.ArrayList;
import java.util.List;

import static sample.Constant.*;
import static sample.GameObject.Direction.*;

public class Snake {
    private ArrayList<BodyPart> wholeBody;
    private Pane fieldPanel;
    private Pane foodPane;

    public Snake(FieldPanel fieldPanel, Pane foodPane) {
        this.fieldPanel = fieldPanel;
        this.foodPane = foodPane;
        wholeBody = new ArrayList<>();
        createSnakeHead();
    }

    private void createSnakeHead() {
        double centerXY = CELL_SIZE * (double) BOARD_LENGTH / 2;
        Rectangle head = new Rectangle(CELL_SIZE, CELL_SIZE);
        head.setLayoutY(centerXY);
        head.setStrokeType(StrokeType.INSIDE);
        head.setStroke(Color.BLACK);
        head.setLayoutX(centerXY);
        head.setFill(SNAKE_HEAD_COLOR);
        wholeBody.add(new BodyPart(head, Direction.NONE));
    }

    public void addBodyPart() {

        BodyPart endPart;
        if(wholeBody.size() == 1){
            endPart = getHead();
        }
        else{
            endPart = getTail();
        }
        Rectangle tailRect = endPart.getRectangle();
        increaseBody(endPart, tailRect);
    }


    private void increaseBody(BodyPart tail, Rectangle tailRect) {
        switch (tail.getDirection()) {
            case UP:
                addToBody(tailRect.getLayoutX(), tailRect.getLayoutY() + CELL_SIZE, tail.getDirection());
                break;
            case RIGHT:
                addToBody(tailRect.getLayoutX() - CELL_SIZE, tailRect.getLayoutY(), tail.getDirection());
                break;
            case DOWN:
                addToBody(tailRect.getLayoutX(), tailRect.getLayoutY() - CELL_SIZE, tail.getDirection());
                break;
            case LEFT:
                addToBody(tailRect.getLayoutX() + CELL_SIZE, tailRect.getLayoutY(), tail.getDirection());
                break;
        }
    }

    private void addToBody(double layoutX, double layoutY, Direction direction) {
        Rectangle partRect = createBodyRectangle(layoutX, layoutY);
        BodyPart part = new BodyPart(partRect, direction);
        wholeBody.add(part);
        fieldPanel.getChildren().add(partRect);
    }

    public void decreaseBodyPart() {
        BodyPart tail = getTail();
        if(tail != null){
            Rectangle tailRect = tail.getRectangle();
            wholeBody.remove(tail);
            fieldPanel.getChildren().remove(tailRect);

        }
    }

    private Rectangle createBodyRectangle(double layoutX, double layoutY) {
        Rectangle partRect = new Rectangle(CELL_SIZE, CELL_SIZE);
        partRect.setFill(SNAKE_BODY_COLOR);
        partRect.setStrokeType(StrokeType.INSIDE);
        partRect.setStroke(Color.BLACK);
        partRect.setLayoutX(layoutX);
        partRect.setLayoutY(layoutY);
        return partRect;
    }

    public void moveBody() {
        BodyPart previous = getHead();
        if (getBody() != null) {
            for (BodyPart bodyPart : getBody()) {
                previous = bodyPart.moveTo(previous);
            }
        }
    }

    public void moveHead(int force, boolean isVertical, boolean isWrapAround) {
        Rectangle head = getHead().getRectangle();
        if (isVertical) {
            double newYLocation = head.getLayoutY() + force;
            if (isWrapAround) {
                newYLocation = getWrappedPosition(newYLocation);
            }
            head.setLayoutY(newYLocation);
            updateDirection(force, DOWN, UP);

        }
        else {
            double newXLocation = head.getLayoutX() + force;
            if (isWrapAround) {
                newXLocation = getWrappedPosition(newXLocation);
            }
            head.setLayoutX(newXLocation);
            updateDirection(force, RIGHT, LEFT);
        }
    }

    private double getWrappedPosition(double newLocation) {
        if (newLocation < 0) {
            newLocation = GAME_WINDOW_LENGTH + newLocation;
        }
        newLocation = newLocation % GAME_WINDOW_LENGTH;
        return newLocation;
    }

    private void updateDirection(int force, Direction firstDirect, Direction secondDirection) {
        if ((force > 0)) {
            getHead().setDirection(firstDirect);
        } else {
            getHead().setDirection(secondDirection);
        }
    }



    public List<BodyPart> getBody() {
        if (wholeBody.isEmpty()) {
            return null;
        }
        if (wholeBody.size() == 1) {
            return null;
        }
        return wholeBody.subList(1, wholeBody.size());
    }

    public List<BodyPart> getWholeBody() {
        return wholeBody;
    }

    public BodyPart getHead() {
        return wholeBody.get(0);
    }

    public BodyPart getTail() {
        if (wholeBody.size() == 1) {
            return null;
        }
        return wholeBody.get(wholeBody.size() - 1);
    }

    public void eat(Circle food) {
        foodPane.getChildren().remove(food);
    }




    public static class BodyPart {
        private Rectangle rectangle;
        //direction  0=nowhere 1=top 2=right 3=down 4=left - will be refactored to constants
        private Direction direction;

        BodyPart(Rectangle rectangle, Direction direction) {
            this.rectangle = rectangle;
            this.direction = direction;
        }

        public BodyPart moveTo(BodyPart otherRectangle) {
            Rectangle tempRectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
            tempRectangle.setLayoutX(rectangle.getLayoutX());
            tempRectangle.setLayoutY(rectangle.getLayoutY());
            BodyPart bodyPart = new BodyPart(tempRectangle, direction);
            rectangle.setLayoutX(otherRectangle.getRectangle().getLayoutX());
            rectangle.setLayoutY(otherRectangle.getRectangle().getLayoutY());
            direction = otherRectangle.getDirection();
            return bodyPart;
        }

        public Rectangle getRectangle() {
            return rectangle;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        @Override
        public String toString() {
            return "(" + rectangle.getLayoutX() + "," + rectangle.getLayoutY() + ")";
        }
    }

}

