package src.java.Project.entities;

import src.java.Project.Point;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by marinamoore on 4/30/15.
 */
public class QuakeTest extends TestCase {

    Quake testQuake = new Quake("quake", null, new Point(1,2), 3);

    public QuakeTest(){
    }

    @Test
    public void testGetAnimationRate(){
        assertEquals(3, testQuake.getAnimationRate());
    }
}