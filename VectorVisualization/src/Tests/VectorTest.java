package Tests;

import Mathematics.Exceptions.IncorrectInputException;
import org.junit.Before;
import org.junit.Test;
import sample.Vector;

import static org.junit.Assert.assertEquals;

public class VectorTest {
    private Vector vector1;
    private Vector vector2;
    private Vector vector3;
    private Vector vector4;
    private Vector vector5;

    @Before
    public void setUp() throws Exception {
        vector1 = new Vector(0, 0, 0, 3);
        vector2 = new Vector(0, 0, 4, 0);
        vector3 = new Vector(0, 0, 4, 3);
        vector4 = new Vector(1, 1, 1, 1);
    }

    @Test(expected = IncorrectInputException.class)
    public void incorrectVector() throws IncorrectInputException {
        Vector vector = new Vector(1, 3, -2, 5);
    }

    @Test
    public void getResultantVector() throws IncorrectInputException {
        assertEquals(vector3, Vector.getResultantVector(vector1, vector2));
    }

    @Test
    public void getDotProduct() throws IncorrectInputException {
        assertEquals(7, Vector.getDotProduct(vector4, vector3), 0.0001);
    }

    @Test
    public void addVector() throws IncorrectInputException {
        Vector vector = new Vector(0, 0, 4, 3);
        vector.addVector(new Vector(1, 1, 1, 1));
        assertEquals(new Vector(1, 1, 5, 4), vector);
    }

    @Test
    public void addScaledVector() throws IncorrectInputException {
        Vector vector = new Vector(0, 0, 4, 3);
        vector.addScaledVector(new Vector(5,5,5,5), 0.2);
        assertEquals(new Vector(1, 1, 5, 4), vector);
    }

    @Test
    public void getX1() {
        assertEquals(vector4.getX1(),1,0.0001);
    }

    @Test
    public void setX1() {
        vector4.setX1(3);
        assertEquals(vector4.getX1(),3,0.0001);
    }

    @Test
    public void getX2() {
        assertEquals(vector4.getX2(),1,0.0001);
    }

    @Test
    public void setX2() {
        vector4.setX2(3);
        assertEquals(vector4.getX1(),3,0.0001);
    }

    @Test
    public void getY1() {
        assertEquals(vector4.getY1(), 1, 0.0001);
    }

    @Test
    public void setY1() {
        vector4.setY1(4);
        assertEquals(vector4.getY1(),4,0.0001);
    }

    @Test
    public void getY2() {
        assertEquals(vector4.getY2(), 1, 0.0001);
    }

    @Test
    public void setY2() {
        vector4.setY2(5);
        assertEquals(vector4.getY2(),5,0.0001);
    }

    @Test
    public void getXDistance() {
        assertEquals(vector3.getXDistance(), 4, 0.0001);
    }

    @Test
    public void getYDistance() {
        assertEquals(vector3.getYDistance(), 3, 0.0001);
    }
}