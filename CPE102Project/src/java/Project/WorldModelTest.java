package src.java.Project;

/**
 * Created by marinamoore on 4/30/15.
 */
public class WorldModelTest {
/*
    public WorldModelTest(){
    }

    WorldModel testWorld = WorldModel.getInstance();
    testWorld.init(5,6,null);
    WorldModel testFullWorld = new WorldModel(7,7,new Background("bg"));
    Vein testVein =  new Vein("vein", 3, new Point(2,3));
    Vein testVein2 = new Vein("vein2", 3, new Point(4,5));
    Ore testOre = new Ore("ore", new Point(1,2),5);
    Blacksmith testSmith = new Blacksmith("smith", new Point(2,2), 4,5);
    Ore testOre2 = new Ore("ore2", new Point(19,4),4);

    @Test
    public void testWithinBounds() throws Exception {
        assertTrue(testFullWorld.withinBounds(new Point(2,2)));
        assertFalse(testWorld.withinBounds(new Point(5,6)));
    }

    @Test
    public void testIsOccupied() throws Exception {
        assertFalse(testWorld.isOccupied(new Point(1, 1)));
        assertFalse(testFullWorld.isOccupied(new Point(10, 4)));

        testWorld.addEntity(testVein);
        assertTrue(testWorld.isOccupied(new Point(2, 3)));
    }

    @Test
    public void testFindNearest() throws Exception{
        testWorld.addEntity(testVein);
        testWorld.addEntity(testVein2);
        assertSame(testVein, testWorld.findNearest(new Point(1, 1), Types.VEIN));

        testWorld.addEntity(testOre);
        assertSame(testOre, testWorld.findNearest(new Point(4, 4), Types.ORE));

        assertSame(null, testWorld.findNearest(new Point(3, 3), Types.ENTITIES));

        testWorld.addEntity(testSmith);
        assertSame(testSmith, testWorld.findNearest(new Point(2, 2), Types.BLACKSMITH));
    }

    @Test
    public void testAddEntity() throws  Exception{
        testWorld.addEntity(testVein);
        assertSame(testVein, testWorld.getTileOccupant(new Point(2, 3)));
        testWorld.addEntity(testOre2);
    }

    @Test
    public void testMoveEntity() throws Exception {
        testWorld.addEntity(testSmith);
        testWorld.moveEntity(testSmith, new Point(1, 1));
        assertSame(testSmith, testWorld.getTileOccupant(new Point(1, 1)));

        testWorld.addEntity(testOre);
        testWorld.moveEntity(testOre, new Point(100, 100));
        assertSame(testOre, testWorld.getTileOccupant(new Point(1, 2)));
    }

    @Test
    public void testRemoveEntity() throws Exception {
        testWorld.addEntity(testSmith);
        testWorld.removeEntity(testSmith);
        assertSame(null, testWorld.getTileOccupant(new Point(2,2)));
    }

    @Test
    public void testRemoveEntityAt() throws Exception {
        testWorld.addEntity(testOre);
        testWorld.removeEntityAt(new Point(1, 2));
        assertSame(null, testWorld.getTileOccupant(new Point(1,2)));
    }

    @Test
    public void testGetTileOccupant() throws Exception {
        testWorld.addEntity(testSmith);
        assertSame(testSmith, testWorld.getTileOccupant(new Point(2, 2)));

        assertSame(null, testWorld.getTileOccupant(new Point(100, 2)));
        assertSame(null, testWorld.getTileOccupant(new Point(1,1)));
    }

    @Test
    public void testGetEntities() throws Exception {
        testWorld.addEntity(testSmith);
        assertSame(testSmith, testWorld.getEntities().get(0));
    }

    @Test
    public void testNextPosition() throws Exception {
        testWorld.addEntity(testOre);
        assertSame(2, testWorld.nextPosition(testOre.getPosition(), new Point(3, 4)).getX());
        assertSame(2, testWorld.nextPosition(testOre.getPosition(), new Point(3, 4)).getX());

        assertSame(4, testWorld.nextPosition(new Point(3,3), new Point(3,7)).getY());
        assertSame(3, testWorld.nextPosition(new Point(3,3), new Point(3,7)).getX());
    }

    @Test
    public void testFindOpenAround() throws Exception {
        testWorld.addEntity(testSmith);
        assertSame(2, testWorld.findOpenAround(new Point(1, 2), 2).getX());
        assertSame(3,testWorld.findOpenAround(new Point(1,2),2).getY());
    }

    @Test
    public void testNearestEntity() throws Exception {
        //tested through findNearest
    }
*/
}