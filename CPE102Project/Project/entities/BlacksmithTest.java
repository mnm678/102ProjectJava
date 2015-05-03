package Project.entities;

import Project.Point;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marinamoore on 4/30/15.
 */
public class BlacksmithTest {

    Blacksmith testSmith = new Blacksmith("blacksmith", new Point(1,2), 3, 4);

    @Test
    public void testGetRate() throws Exception {
        assertEquals(4, testSmith.getRate());
    }

    @Test
    public void testGetResourceCount() throws Exception {
        assertEquals(0, testSmith.getResourceCount());
    }

    @Test
    public void testSetResourceCount() throws Exception {
        testSmith.setResourceCount(5);
        assertEquals(5, testSmith.getResourceCount());
    }
}