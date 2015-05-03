package Project.entities;

import Project.Point;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marinamoore on 4/30/15.
 */
public class MinerFullTest {
    MinerFull testMiner = new MinerFull("miner", 3, new Point(1,2), 4, 5);

    public MinerFullTest(){
    }


    /* full miners don't need a set resource count
    @Test
    public void testSet_resource_count() throws Exception {
        testMiner.setResourceCount(5);
        assertEquals(5, testMiner.getResourceCount());
    }*/

    @Test
    public void testGet_resource_count() throws Exception {
        assertEquals(3, testMiner.getResourceCount());
    }

    @Test
    public void testGet_resource_limit() throws Exception {
        assertEquals(3, testMiner.getResourceLimit());
    }

    @Test
    public void testGet_animation_rate() throws Exception {
        assertEquals(5, testMiner.getAnimationRate());
    }
}