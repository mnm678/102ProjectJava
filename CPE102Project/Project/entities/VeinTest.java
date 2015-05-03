package Project.entities;

import Project.Point;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by marinamoore on 4/30/15.
 */
public class VeinTest extends TestCase {
    public VeinTest(){
    }

    Vein testVein = new Vein("vein", 3, new Point(1,2));

    @Test
    public void testGetResourceDistance(){
        assertEquals(1, testVein.getResourceDistance());
    }

    @Test
    public void testGetRate(){
        assertEquals(3, testVein.getRate());
    }

    @Test
    public void testGetName(){
        assertEquals("vein", testVein.getName());
    }

    @Test
    public void testGetPosition(){
        assertEquals(1.0, testVein.getPosition().getX());
        assertEquals(2.0, testVein.getPosition().getY());
    }

    @Test
    public void testSetPosition() {
        testVein.setPosition(new Point(3,4));
        assertEquals(3.0, testVein.getPosition().getX());
        assertEquals(4.0, testVein.getPosition().getY());
    }
}