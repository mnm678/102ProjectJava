package src.java.Project;
import processing.core.PImage;
import src.java.Project.entities.Entity;
import src.java.Project.entities.InteractiveEntity;

import java.util.ArrayList;
import java.util.List;

public class WorldModel{

    private static WorldModel instance = null;
    protected WorldModel(){}

    private Grid background;
    private int numRows;
    private int numCols;
    private Grid occupancy;
    private List<InteractiveEntity> entities;

    public static WorldModel getInstance(){
        if(instance == null){
            instance =  new WorldModel();
        }
        return instance;
    }

    public void init(int numRows, int numCols, Entity bg){
        this.background = new Grid(numCols, numRows, bg);
        this.numRows = numRows;
        this.numCols = numCols;
        this.occupancy = new Grid(numCols, numRows, null);
        this.entities = new ArrayList<InteractiveEntity>();
    }

    public int getNumRows(){
        return this.numRows;
    }

    public int getNumCols(){
        return this.numCols;
    }

    public boolean withinBounds(Point pt){
        return (pt.getX() >= 0 && pt.getX() < this.numCols &&
                pt.getY() >= 0 && pt.getY() < this.numRows);
    }

    public boolean isOccupied(Point pt){
        return (this.withinBounds(pt) &&
        !(this.occupancy.getCell(pt) == null));
    }


    public Entity findNearest(Point pt, Types type){
        List<InteractiveEntity> ofType;
        List<Double> loc;
        ofType = new ArrayList<>();
        loc = new ArrayList<>();

        for(Entity e : this.entities) {
            if (type == e.getType()) {
                InteractiveEntity e2 = (InteractiveEntity) e;
                ofType.add(e2);
                loc.add(pt.distanceSq(e2.getPosition()));
            }
        }
        return nearestEntity(ofType, loc);
    }


    public void addEntity(InteractiveEntity entity){
        Point pt = entity.getPosition();
        if(this.withinBounds(pt)){
            Entity oldEntity = this.occupancy.getCell(pt);
            if(oldEntity != null){
                //entity.clearPendingActions(oldEntity)
            }
            this.occupancy.setCell(pt,entity);
            this.entities.add(entity);
        }
    }

    public List<Point> moveEntity(InteractiveEntity entity, Point pt){
        List<Point> tiles;
        tiles = new ArrayList<Point>();
        if(this.withinBounds(pt)){
            Point oldPt = entity.getPosition();
            this.occupancy.setCell(oldPt, null);
            tiles.add(pt);
            this.occupancy.setCell(pt,entity);
            entity.setPosition(pt);
        }
        return tiles;
    }

    public void removeEntity(InteractiveEntity entity){
        this.removeEntityAt(entity.getPosition());
    }

    public void removeEntityAt(Point pt){
        if(this.withinBounds(pt) &&
                this.occupancy.getCell(pt) != null){
            InteractiveEntity entity = (InteractiveEntity) this.occupancy.getCell(pt);
            entity.setPosition(new Point(pt.getX()-1, pt.getY()-1));
            this.entities.remove(entity);
            this.occupancy.setCell(pt, null);
        }
    }

    //public void scheduleAction(action)

    //public void unscheduleAction(action)

    //updateOnTime

    public PImage getBackgroundImage(Point pt){
        if(this.withinBounds(pt)){
            return this.background.getCell(pt).getImage();
        }
        else {
            return null;
        }
    }

    public Entity getBackground(Point pt){
        if(this.withinBounds(pt)){
            return this.background.getCell(pt);
        }
        else{
            return null;
        }
    }

    public void setBackground(Point pt, Entity bgnd){
        if(this.withinBounds(pt)){
            this.background.setCell(pt, bgnd);
        }
    }

    public Entity getTileOccupant(Point pt){
        if(this.withinBounds(pt)){
            return this.occupancy.getCell(pt);
        }
        else{
            return null;
        }
    }

    public List<InteractiveEntity> getEntities(){
        return this.entities;
    }

    public Point nextPosition(Point entityPt, Point destPt){
        int horiz = Integer.signum(destPt.getX() - entityPt.getX());
        Point newPt = new Point(entityPt.getX() + horiz, entityPt.getY());

        if(horiz == 0 || this.isOccupied(newPt)){
            int vert = Integer.signum(destPt.getY() - entityPt.getY());
            newPt = new Point(entityPt.getX(), entityPt.getY() + vert);

            if(vert ==0 || this.isOccupied(newPt)){
                newPt = new Point(entityPt.getX(), entityPt.getY());
            }
        }
        return newPt;
    }

    //createBlob

    public Point findOpenAround(Point pt, int distance){
        for(int dy = distance - 1; dy < distance + 2; dy++){
            for(int dx = distance - 1; dx < distance + 2; dx++){
                Point newPt;
                newPt = new Point(pt.getX() + dx, pt.getY() + dy);
                if(this.withinBounds(newPt) && !(this.isOccupied(newPt))){
                    return newPt;
                }
            }
        }
        return null;
    }

    //createOre

    //createQuake

    //createVein

    //createAnimationAction

    //scheduleAnimation

    //handleMouseButton

    public InteractiveEntity nearestEntity(List<InteractiveEntity> e, List<Double> dist){
        InteractiveEntity ePair = null;
        if(e.size() > 0){
            ePair = e.get(0);
            double distPair = dist.get(0);
            for(int i = 0; i < e.size(); i++){
                if(dist.get(i) < distPair){
                    distPair = dist.get(i);
                    ePair = e.get(i);
                }
            }
        }
        return ePair;
    }
}