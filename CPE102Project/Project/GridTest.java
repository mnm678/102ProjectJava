package Project;

import Project.entities.Blacksmith;
import Project.entities.Vein;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marinamoore on 4/30/15.
 */
public class GridTest {

    public GridTest(){}

    Vein testVein = new Vein("vein", 1, new Point(0,0));

    Grid testGrid =  new Grid(5,6,null);
    Grid testFullGrid = new Grid(2,3, testVein);

    @Test
    public void testSetCell() throws Exception {
        Blacksmith smith = new Blacksmith("smith", new Point(1,2), 3, 4);
        testGrid.setCell(new Point(2,2),smith);
        assertSame(smith, testGrid.getCell(new Point(2,2)));
    }

    @Test
    public void testGetCell() throws Exception {
        assertSame(null, testGrid.getCell(new Point(1,1)));
        assertSame(testVein, testFullGrid.getCell(new Point(1,1)));
    }
}