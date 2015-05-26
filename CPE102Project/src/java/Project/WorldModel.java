package src.java.Project;
import processing.core.PImage;
import src.java.Project.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
        this.entities = new ArrayList<>();
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
        ArrayList<InteractiveEntity> ofType = new ArrayList<>();
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
            if(newsmallest < smallest){
                smallest = newsmallest;
                mindex = ofType.indexOf(i);
            }
        }
        return ofType.get(mindex);
    }

    public void addEntity(InteractiveEntity entity){
        Point pt = entity.getPosition();
        if(this.withinBounds(pt)){
            this.occupancy.setCell(pt,entity);
            this.entities.add(entity);
        }
    }

    public List<Point> moveEntity(InteractiveEntity entity, Point pt){
        List<Point> tiles;
        tiles = new ArrayList<>();
        if(this.withinBounds(pt)){
            Point oldPt = entity.getPosition();
            this.occupancy.setCell(oldPt, null);
            tiles.add(pt);
            this.occupancy.setCell(pt, entity);
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
        }
    }

    public void scheduleAction(Actions action, long time){
        this.actionQueue.insert(action, time);
    }

    public void unscheduleAction(Actions action){
        this.actionQueue.remove(action);
    }

    public void updateOnTime(long ticks){
        ListItem next = this.actionQueue.head();
        while(next != null && next.getOrd() < ticks){
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

    /*private static class AElement{
        private Point point;
        private int gScore;
        private int hScore;
        private int fScore;
        private Point cameFrom;

        public AElement(Point point, int gScore, int hScore, Point cameFrom){
            this.point = point;
            int fScore = gScore + hScore;
            this.gScore = gScore;
            this.hScore = hScore;
            this.cameFrom = cameFrom;
        }

        public int getfScore(){
            return this.fScore;
        }

        public int getgScore(){
            return this.gScore;
        }

        public int gethScore(){
            return this.hScore;
        }

        public Point getPoint(){
            return this.point;
        }
    }
*/

    public int heuristicCostEstimate(Point start, Point goal){
        int x = Math.abs(start.getX() - goal.getX());
        int y = Math.abs(start.getY() - goal.getY());
        return x+y;
    }

    public List<Point> reconstructPath(HashMap<Point, Point> cameFrom, Point current){
        //System.out.println("reconstructPath");
        List<Point> totalPath = new ArrayList<>();
        totalPath.add(current);
        while(cameFrom.containsKey(current)){
            current = cameFrom.get(current);
            totalPath.add(current);
        }
        return totalPath;
    }

    public AReturn ANextPosition(Point entityPt, Point destPt){

        //List<AElement> closedSet = new ArrayList<>();
        //List<AElement> openSet = new ArrayList<>();

        List<Point> path;

        HashSet<Point> searched = new HashSet<>();

        HashSet<Point> closedSet = new HashSet<>();
        HashSet<Point> openSet = new HashSet<>();

        HashMap<Point, Integer> gscore = new HashMap<>();
        HashMap<Point, Integer> fscore = new HashMap<>();
        HashMap<Point, Point> cameFrom = new HashMap<>();

        openSet.add(entityPt);
        searched.add(entityPt);
        gscore.put(entityPt, 0);
        fscore.put(entityPt, gscore.get(entityPt) + heuristicCostEstimate(entityPt, destPt));

        //Point current = entityPt;

        while (!openSet.isEmpty()){

            //System.out.println("hi");
            int temp = 1000000000;
            Point current = null;

            for(Point p : openSet){
                //System.out.println(p.getX());
                //System.out.println(p.getY());
                if(fscore.get(p)< temp){
                    //System.out.println("hit");
                    current = p;
                    temp = fscore.get(p);
                }
            }

            //System.out.println("x: " + current.getX());
            //System.out.println("y: " + current.getY());

            if(current.equals(destPt)){
                //System.out.println("found destination");
                path = reconstructPath(cameFrom, destPt);
                int length = path.size();
                Point returnPoint;
                if(length>2) {
                    returnPoint = path.get(length - 2);
                }
                else{
                    //System.out.println("hit");
                    //return path.get(length - 1);
                    returnPoint = destPt;
                }

                return new AReturn(returnPoint, path, searched);
            }

            openSet.remove(current);
            closedSet.add(current);

            List<Point> neighbors = openPoints(current, destPt);
            for(Point p : neighbors){
                if(closedSet.contains(p)){
                    continue;
                }
                int tentativeGScore = gscore.get(current) + 1;

                if(! openSet.contains(p) || tentativeGScore < gscore.get(p)){
                    cameFrom.put(p, current);
                    gscore.put(p,tentativeGScore);
                    fscore.put(p,gscore.get(p) + heuristicCostEstimate(p,destPt));

                    if(! openSet.contains(p)){
                        openSet.add(p);
                        searched.add(p);
                    }
                }
            }
        }
        return new AReturn(entityPt, null, null);
/*
        int gScore = 0;
        int hScore = heuristicCostEstimate(entityPt, destPt);
        openSet.add(new AElement(entityPt, gScore, hScore, null));

        while(openSet.size() > 0){
            AElement current = openSet.get(0);
            for(AElement p : openSet){
                if(current.getfScore() < p.getfScore()){
                    current = p;
                }
            }

            if(current.getPoint().equals(destPt)){
                //you reached the goal, do stuff
            }

            openSet.remove(current);
            closedSet.add(current);

            List<Point> neighbors = openPoints(current.getPoint());
            for(Point p :neighbors){
                Boolean inClosed = isInSet(closedSet,p);
                Boolean inOpen = isInSet(openSet,p);
                int tentativegScore;
                if(!inClosed){
                    tentativegScore = current.getgScore() + 1;
                    }
                if(!inOpen || found better path){

                }
            }

        }
*/
    }
/*
    public Boolean isInSet(List<AElement> set, Point p){
        Boolean in = false;
        for(AElement e : set){
            if(e.getPoint().equals(p)){
                in = true;
            }
        }
        return in;
    }
    */

    public List<Point> openPoints(Point start, Point goal){
        List<Point> temp = new ArrayList<>();
        List<Point> open = new ArrayList<>();
        int x = start.getX();
        int y = start.getY();
        temp.add(new Point(x-1,y));
        temp.add(new Point(x+1,y));
        temp.add(new Point(x, y-1));
        temp.add(new Point(x, y+1));
        for(Point p : temp){
            if(this.withinBounds(p) && !this.isOccupied(p) || p.equals(goal)){
                open.add(p);
            }
        }
        return open;
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
        blob.scheduleBlob(ticks);
        return blob;
    }

    public Ore createOre(String name, Point pt, long ticks){
        Ore ore = new Ore(name, Load.map.get("ore"), pt,
                oreCorruptMin + (int)Math.random() * oreCorruptMax
        );
        ore.scheduleOre(ticks);
        return ore;
    }

    public Quake createQuake(Point pt, long ticks){
        Quake quake = new Quake("quake", Load.map.get("quake"), pt,
                quakeAnimationRate
        );
        quake.scheduleQuake(ticks);
        return quake;
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

            entity.removePendingAction(temp[0]);

            entity.nextImage();

            if(repeatCount != 1){
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

    public void actionsClearPendingActions(PendingActions entity){
        for(Actions action : entity.getPendingActions()){
            unscheduleAction(action);
        }
        entity.clearPendingActions();
    }

    public Boolean adjacent(Point p1, Point p2){
        Boolean temp1 =  (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1);
        Boolean temp2 =  (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
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