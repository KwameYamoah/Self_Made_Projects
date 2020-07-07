package main;

import Exceptions.IncorrectInputException;

import java.awt.*;


public class StraightLine {
    private double x1;
    private double x2;
    private double y1;
    private double y2;

    public StraightLine(double x1, double y1, double x2, double y2) throws IncorrectInputException {
        setValues(x1, y1, x2, y2);

    }

    public StraightLine(StraightLine straightLine) throws IncorrectInputException {
        setValues(straightLine.getX1(), straightLine.getY1(), straightLine.getX2(), straightLine.getY2());
    }

    public StraightLine(Point a, Point b) throws IncorrectInputException {
        setValues(a.x, a.y, b.x, b.y);
    }

    private void setValues(double x1, double y1, double x2, double y2) throws IncorrectInputException {
        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0)
            throw new IncorrectInputException("Value for coordinates cannot be negative");
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    private void setValues(StraightLine straightLine) throws IncorrectInputException {
        setValues(straightLine.x1, straightLine.y1, straightLine.x2, straightLine.y2);
    }


    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof StraightLine)) return false;
        else{
            if(this.x1 == ((StraightLine) obj).x1 && this.y1 == ((StraightLine) obj).y1 &&
                    this.x2 == ((StraightLine) obj).x2 && this.y2 == ((StraightLine) obj).y2){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "X1 " + x1  + " Y1 " + y1 + " X2 " + x2  + " Y2 " + y2;
    }
}


