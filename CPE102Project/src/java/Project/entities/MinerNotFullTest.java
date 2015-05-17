package src.java.Project.entities;

import src.java.Project.Point;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marinamoore on 5/1/15.
 */
public class MinerNotFullTest {

    public MinerNotFullTest(){
    }

    MinerNotFull testMiner = new MinerNotFull("miner", null, 2, new Point(1,2),3, 4);

    @Test
    public void testGetResourceCount() throws Exception {
        assertEquals(0, testMiner.getResourceCount());
    }

    @Test
    public void testSetResourceCount() throws Exception {
        testMiner.setResourceCount(1);
        assertEquals(1, testMiner.getResourceCount());
    }
}