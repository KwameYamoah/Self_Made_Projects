package Tests;

import Exceptions.IncorrectInputException;
import org.junit.Before;
import org.junit.Test;
import main.StraightLine;

import static org.junit.Assert.assertEquals;

public class StraightLineTest {

    private StraightLine straightLine;


    @Before
    public void setUp() throws Exception {
        straightLine = new StraightLine(1, 1, 1, 1);
    }

    @Test(expected = IncorrectInputException.class)
    public void incorrectLine() throws IncorrectInputException {
        new StraightLine(1, 3, -2, 5);
    }


    @Test
    public void getX1() {
        assertEquals(straightLine.getX1(), 1, 0.0001);
    }

    @Test
    public void setX1() {
        straightLine.setX1(3);
        assertEquals(straightLine.getX1(), 3, 0.0001);
    }

    @Test
    public void getX2() {
        assertEquals(straightLine.getX2(), 1, 0.0001);
    }

    @Test
    public void setX2() {
        straightLine.setX2(3);
        assertEquals(straightLine.getX2(), 3, 0.0001);
    }

    @Test
    public void getY1() {
        assertEquals(straightLine.getY1(), 1, 0.0001);
    }

    @Test
    public void setY1() {
        straightLine.setY1(4);
        assertEquals(straightLine.getY1(), 4, 0.0001);
    }

    @Test
    public void getY2() {
        assertEquals(straightLine.getY2(), 1, 0.0001);
    }

    @Test
    public void setY2() {
        straightLine.setY2(5);
        assertEquals(straightLine.getY2(), 5, 0.0001);
    }
}
