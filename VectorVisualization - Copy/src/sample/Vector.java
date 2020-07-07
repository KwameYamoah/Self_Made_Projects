package sample;

import Exceptions.IncorrectInputException;

import java.awt.*;


public class Vector {
    private double x1;
    private double x2;
    private double y1;
    private double y2;

    public Vector(double x1, double y1, double x2, double y2) throws IncorrectInputException {
        setValues(x1, y1, x2, y2);

    }

    public Vector(double magnitude, double angle) throws IncorrectInputException {
        //cos(angle)  = adj / hyp
        double x2 = Math.cos(Math.toRadians(angle)) * magnitude;
        double y2 = Math.sin(Math.toRadians(angle)) * magnitude;
        setValues(0, 0, x2, y2);
    }

    public Vector(Vector vector) throws IncorrectInputException {
        setValues(vector.getX1(), vector.getY1(), vector.getX2(), vector.getY2());
    }

    public Vector(Point a, Point b) throws IncorrectInputException {
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

    private void setValues(Vector vector) throws IncorrectInputException {
        setValues(vector.x1, vector.y1, vector.x2, vector.y2);
    }

    public double getAngle() {
        return Math.toDegrees(Math.atan(getYDistance() / getXDistance()));
    }

    public double getMagnitude() {
        double xDistanceSquared = Math.pow(getXDistance(), 2);
        double yDistanceSquared = Math.pow(getYDistance(), 2);
        return Math.sqrt(xDistanceSquared + yDistanceSquared);
    }

    public static Vector getResultantVector(Vector vector1, Vector vector2) throws IncorrectInputException {
        return new Vector(
                Math.abs(vector1.x1 + vector2.x1),
                Math.abs(vector1.y1 + vector2.y1),
                Math.abs(vector1.x2 + vector2.x2),
                Math.abs(vector1.y2 + vector2.y2));
    }

    public static double getDotProduct(Vector vector1, Vector vector2) throws IncorrectInputException {
        return Math.abs(vector1.x1 * vector2.x1) + Math.abs(vector1.y1 * vector2.y1) + Math.abs(vector1.x2 * vector2.x2) + Math.abs(vector1.y2 * vector2.y2);
    }


    public void addVector(Vector vector) throws IncorrectInputException {
        setValues(getResultantVector(this, vector));
    }

    public void addScaledVector(Vector force, double factor) throws IncorrectInputException {
        addVector(new Vector(force.x1 * factor, force.y1 * factor,
                force.x2 * factor, force.y2 * factor));
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

    public double getXDistance() {
        return Math.abs(x2 - x1);
    }

    public double getYDistance() {
        return Math.abs(y2 - y1);
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Vector)) return false;
        else{
            if(this.x1 == ((Vector) obj).x1 && this.y1 == ((Vector) obj).y1 &&
                    this.x2 == ((Vector) obj).x2 && this.y2 == ((Vector) obj).y2){
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


