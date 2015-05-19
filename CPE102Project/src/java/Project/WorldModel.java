package src.java.Project;
import com.sun.org.apache.xpath.internal.operations.Bool;
import processing.core.PImage;
import src.java.Project.entities.*;

import javax.swing.*;
import java.lang.reflect.Array;
import java.sql.Blob;
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
    public OrderedList actionQueue;

    public final int blobRateScale = 4;
    private final int blobAnimationRateScale = 50;
    private final int blobAnimationMin = 1;
    private final int blobAnimationMax = 3;

    private final int oreCorruptMin = 20000;
    private final int oreCorruptMax = 30000;

    public final int quakeSteps = 10;
    public final int quakeDuration = 1100;
    private final int quakeAnimationRate = 100;

    private final int veinSpawnDelay = 500;
    private final int veinRateMin = 8000;
    private final int veinRateMax = 17000;

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
        this.actionQueue = new OrderedList();
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
        ArrayList<InteractiveEntity> ofType = new ArrayList<InteractiveEntity>();
        int mindex = 0;
        for(InteractiveEntity e : this.entities){
            if(type == e.getType()){
                ofType.add(e);
            }
        }
        if(ofType.size() == 0){
            return null;
        }
        double smallest = ofType.get(0).getPosition().distanceSq(pt);
        for(InteractiveEntity i : ofType){
            double newsmallest = i.getPosition().distanceSq(pt);
            System.out.println("check: " + i.getPosition().getX() + " " + i.getPosition().getY() + " : " + newsmallest);
            if(newsmallest < smallest){
                System.out.println("\tnearer");
                smallest = newsmallest;
                mindex = ofType.indexOf(i);
            }
        }
        return ofType.get(mindex);
    }

    /*
    These don't work for some reason, replaced with the above
    public Entity findNearest(Point pt, Types type){
        List<InteractiveEntity> ofType;
        List<Double> loc;
        ofType = new ArrayList<>();
        loc = new ArrayList<>();

        for(Entity e : this.entities) {
            if (type.equals(e.getType())) {
                //System.out.println("in findNearest");
                InteractiveEntity e2 = (InteractiveEntity) e;
                ofType.add(e2);
                loc.add(pt.distanceSq(e2.getPosition()));
            }
        }
        return nearestEntity(ofType, loc);
    }

    public InteractiveEntity nearestEntity(List<InteractiveEntity> e, List<Double> dist) {
        InteractiveEntity ePair = null;
        //System.out.println("in nearestEntity1");
        if (e.size() > 0) {
            //System.out.println("in nearestEntity2");
            ePair = e.get(0);
            double distPair = dist.get(0);
            for (int i = 0; i < e.size(); i++) {
                if (dist.get(i) < distPair) {
                    distPair = dist.get(i);
                    ePair = e.get(i);
                }
            }
        }
        return ePair;
    }
*/
    public void addEntity(InteractiveEntity entity){
        Point pt = entity.getPosition();
        if(this.withinBounds(pt)){
            Entity oldEntity = this.occupancy.getCell(pt);
            if(oldEntity instanceof PendingActions){
                //oldEntity.clearPendingActions();
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
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            this.occupancy.setCell(pt, null);
            /*if(entity instanceof PendingActions){
                List<Actions> actions = ((PendingActions) entity).getPendingActions();
                for(Actions action : actions) {
                    ((PendingActions) entity).removePendingAction(action);
                }
            }*/
        }
    }

    public void scheduleAction(Actions action, long time){
        //System.out.println(action);
        this.actionQueue.insert(action, time);
        //System.out.println("add action");
    }

    public void unscheduleAction(Actions action){
        this.actionQueue.remove(action);
    }

    public void updateOnTime(long ticks){
        //System.out.println(actionQueue);
        ListItem next = this.actionQueue.head();
        //System.out.println(next.getOrd());
        while(next != null && next.getOrd() < ticks){
            //System.out.println(next);
            this.actionQueue.pop();
            next.getItem().doAction(ticks);
            next = this.actionQueue.head();
        }
    }

    public PImage getBackgroundImage(Point pt){
        if(this.withinBounds(pt)){
            return this.background.getCell(pt).getImage();
        }
        else {
            return null;
        }
    }


    //only used in save
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
        int horiz = sign(destPt.getX() - entityPt.getX());
        Point newPt = new Point(entityPt.getX() + horiz, entityPt.getY());

        if(horiz == 0 || this.isOccupied(newPt)){
            int vert = sign(destPt.getY() - entityPt.getY());
            newPt = new Point(entityPt.getX(), entityPt.getY() + vert);

            if(vert ==0 || this.isOccupied(newPt)){
                newPt = new Point(entityPt.getX(), entityPt.getY());
            }
        }
        return newPt;
    }

    public Point findOpenAround(Point pt, int distance){
        for(int dy = - 1; dy < distance + 1; dy++){
            for(int dx = - 1; dx < distance + 1; dx++){
                Point newPt;
                newPt = new Point(pt.getX() + dx, pt.getY() + dy);
                if(this.withinBounds(newPt) && !(this.isOccupied(newPt))){
                    return newPt;
                }
            }
        }
        return null;
    }

    public OreBlob createBlob(String name, Point pt, int rate, long ticks){
        OreBlob blob = new OreBlob(name, Load.map.get("blob"),
                pt, rate, (blobAnimationMin + (int)Math.random()*blobAnimationMax) * blobAnimationRateScale);
        blob.scheduleBlob(this, ticks);
        return blob;
    }

    public Ore createOre(String name, Point pt, long ticks){
        Ore ore = new Ore(name, Load.map.get("ore"), pt,
                oreCorruptMin + (int)Math.random() * oreCorruptMax
        );
        ore.scheduleOre(this, ticks);
        return ore;
    }

    public Quake createQuake(Point pt, long ticks){
        Quake quake = new Quake("quake", Load.map.get("quake"), pt,
                quakeAnimationRate
        );
        System.out.println("createQuake");
        quake.scheduleQuake(this, ticks);
        return quake;
    }

    //I can't find where this was used even in Python
    public Vein createVein(String name, Point pt, long ticks){
        Vein vein = new Vein("vein" + name, Load.map.get("ore"),
                veinRateMin + (int)Math.random() * veinRateMax, pt
        );
        return vein;
    }

    public Actions createAnimationAction(Miner entity, int repeatCount){
        Actions [] temp = {null};
        temp[0] = (long currentTicks) ->{

            entity.removePendingAction(temp[0]);

            entity.nextImage();

            if(repeatCount != 1){
                actionScheduleAction(entity,createAnimationAction(entity, Math.max(repeatCount - 1, 0)),
                        currentTicks + entity.getAnimationRate());
            }
        };
        return temp[0];
    }

    public Actions createAnimationAction(AnimationRate entity, int repeatCount){
        Actions [] temp = {null};
        temp[0] = (long currentTicks) ->{
            //System.out.println("AnimationRate createAnimationAction");

            entity.removePendingAction(temp[0]);

            entity.nextImage();

            if(repeatCount != 1){
                //System.out.println("in repeat count");
                actionScheduleAction(entity,createAnimationAction(entity, Math.max(repeatCount - 1, 0)),
                        currentTicks + entity.getAnimationRate());
            }
        };
        return temp[0];
    }

    public void actionScheduleAction(PendingActions entity, Actions action, long time){
        entity.addPendingAction(action);
        scheduleAction(action, time);
    }

    public void scheduleAnimation(Miner entity){
        actionScheduleAction(entity, createAnimationAction(entity, 0), entity.getAnimationRate());
    }

    public void scheduleAnimation(AnimationRate entity, int repeatCount){
        actionScheduleAction(entity, createAnimationAction(entity, repeatCount), entity.getAnimationRate());
    }

    //handleMouseButton

    public void actionsClearPendingActions(PendingActions entity){
        for(Actions action : entity.getPendingActions()){
            unscheduleAction(action);
        }
        entity.clearPendingActions();
    }

    public Boolean adjacent(Point p1, Point p2){
        //System.out.println(Math.abs(p1.getX() - p2.getX()));
        Boolean temp1 =  (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1);
        Boolean temp2 =  (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
        //System.out.println(temp1 || temp2);
        return (temp1 || temp2);
    }

    public int sign(int x){
        if(x<0){
            return -1;
        }
        else if(x>0){
            return 1;
        }
        else{
            return 0;
        }
    }
}