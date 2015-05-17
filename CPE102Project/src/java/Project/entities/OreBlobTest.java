package src.java.Project.entities;

import src.java.Project.Point;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by marinamoore on 4/30/15.
 */
public class OreBlobTest extends TestCase {

    public OreBlob testBlob = new OreBlob("blob", null, new Point(1,2), 5, 6);

    public OreBlobTest(){
    }

    @Test
    public void testGetRate() {
        assertEquals(5, testBlob.getRate());
    }
}