package Project;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by marinamoore on 4/30/15.
 */

public class PointTest extends TestCase {
    public PointTest(){
    }

    Point test = new Point(3,4);
    Point test2 = new Point(0,0);

    @Test
    public void testGetX(){
        assertEquals(3.0, test.getX());
    }

    @Test
    public void testGetY(){
        assertEquals(4.0,test.getY());
    }

    @Test
    public void testDistance(){
        assertEquals(5.0, test.distance(test2));
    }

    @Test
    public void testDistanceSq(){
        assertEquals(25.0, test.distanceSq(test2));
    }
}