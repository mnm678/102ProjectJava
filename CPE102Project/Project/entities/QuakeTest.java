package Project.entities;

import Project.Point;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by marinamoore on 4/30/15.
 */
public class QuakeTest extends TestCase {

    Quake testQuake = new Quake("quake", new Point(1,2), 3);

    public QuakeTest(){
    }

    @Test
    public void testGetAnimationRate(){
        assertEquals(3, testQuake.getAnimationRate());
    }
}