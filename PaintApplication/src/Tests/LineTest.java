package Tests;

import Exceptions.IncorrectInputException;
import org.junit.Before;
import org.junit.Test;
import main.Line;

import static org.junit.Assert.assertEquals;

public class LineTest {

    private Line line;


    @Before
    public void setUp() throws Exception {
        line = new Line(1, 1, 1, 1);
    }

    @Test(expected = IncorrectInputException.class)
    public void incorrectLine() throws IncorrectInputException {
        Line line = new Line(1, 3, -2, 5);
    }


    @Test
    public void getX1() {
        assertEquals(line.getX1(), 1, 0.0001);
    }

    @Test
    public void setX1() {
        line.setX1(3);
        assertEquals(line.getX1(), 3, 0.0001);
    }

    @Test
    public void getX2() {
        assertEquals(line.getX2(), 1, 0.0001);
    }

    @Test
    public void setX2() {
        line.setX2(3);
        assertEquals(line.getX2(), 3, 0.0001);
    }

    @Test
    public void getY1() {
        assertEquals(line.getY1(), 1, 0.0001);
    }

    @Test
    public void setY1() {
        line.setY1(4);
        assertEquals(line.getY1(), 4, 0.0001);
    }

    @Test
    public void getY2() {
        assertEquals(line.getY2(), 1, 0.0001);
    }

    @Test
    public void setY2() {
        line.setY2(5);
        assertEquals(line.getY2(), 5, 0.0001);
    }
}
