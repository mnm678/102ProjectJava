package src.java.Project;
import processing.core.PConstants;
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

    private final int turtleAnimationRate = 100;
    private final int turtleAnimationMin = 1;
    private final int turtleAnimationMax = 3;

    private final int oreCorruptMin = 20000;
    private final int oreCorruptMax = 30000;

    public final int quakeSteps = 10;
    public final int quakeDuration = 1100;
    private final int quakeAnimationRate = 100;

    public List<PImage> ice;

    public static WorldModel getInstance(){
        if(instance == null){
            instance =  new WorldModel();
        }
        return instance;
    }

    public void init(int numRows, int numCols, Entity bg, List<PImage> ice){
        this.background = new Grid(numCols, numRows, bg);
        this.numRows = numRows;
        this.numCols = numCols;
        this.occupancy = new Grid(numCols, numRows, null);
        this.entities = new ArrayList<>();
        this.actionQueue = new OrderedList();
        this.ice = ice;
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

    public int heuristicCostEstimate(Point start, Point goal){
        int x = Math.abs(start.getX() - goal.getX());
        int y = Math.abs(start.getY() - goal.getY());
        return x+y;
    }

    public List<Point> reconstructPath(HashMap<Point, Point> cameFrom, Point current){
        List<Point> totalPath = new ArrayList<>();
        totalPath.add(current);
        while(cameFrom.containsKey(current)){
            current = cameFrom.get(current);
            totalPath.add(current);
        }
        totalPath.remove(current);
        return totalPath;
    }

    public AReturn ANextPosition(Point entityPt, Point destPt){

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

        while (!openSet.isEmpty()){

            int temp = 1000000000;
            Point current = null;

            for(Point p : openSet){
                if(fscore.get(p)< temp){
                    current = p;
                    temp = fscore.get(p);
                }
            }

            if(current.equals(destPt)){
                path = reconstructPath(cameFrom, destPt);
                int length = path.size();
                Point returnPoint;
                if(length>1) {
                    returnPoint = path.get(length - 1);
                }
                else{
                    returnPoint = destPt;
                }

                if(checkIce(returnPoint)){
                    returnPoint = entityPt;
                }

                return new AReturn(returnPoint, path, searched);
            }

            openSet.remove(current);
            closedSet.add(current);

            List<Point> neighbors = openPoints(current, destPt, entityPt);
            for(Point p : neighbors){
                if(closedSet.contains(p)){
                    continue;
                }

                int cost = 1;
                if(ice.contains(getBackgroundImage(p))){
                    cost = 4;
                }

                int tentativeGScore = gscore.get(current) + cost;

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

    }

    public List<Point> openPoints(Point start, Point goal, Point entityPt){
        List<Point> temp = new ArrayList<>();
        List<Point> open = new ArrayList<>();
        int x = start.getX();
        int y = start.getY();
        temp.add(new Point(x-1,y));
        temp.add(new Point(x+1,y));
        temp.add(new Point(x, y - 1));
        temp.add(new Point(x, y + 1));
        for(Point p : temp){
            if(this.withinBounds(p) && !this.isOccupied(p) || p.equals(goal)){
                open.add(p);
            }
            /*if(this.withinBounds(p) && this.getTileOccupant(entityPt).getType() == Types.TURTLE &&
                    this.isOccupied(p) && this.getTileOccupant(p).getType() == Types.OBSTACLE){
                open.add(p);
            }*/
        }
        return open;
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

    public Turtle createTurtle(String name, Point pt, int rate, long ticks){
        Turtle turtle = new Turtle(name, Load.map.get("turtle"),
                pt, rate,
                (turtleAnimationMin + (int)Math.random()* turtleAnimationMax) * turtleAnimationRate);
        turtle.scheduleTurtle(ticks);
        return turtle;
    }

    public OreBlob createBlob(String name, Point pt, int rate, long ticks){
        OreBlob blob = new OreBlob(name, Load.map.get("blob"),
                pt, rate,
                (blobAnimationMin + (int)Math.random()*blobAnimationMax) * blobAnimationRateScale);
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

    public void worldEvent(Point origin, long ticks){
        for(int i=-2; i<3; i++){
            for(int j = -2; j < 3; j++){
                Point temp = new Point(origin.getX() + i, origin.getY() + j);
                setBackground(temp, new Background("newBackground", ice));
            }
        }
        Point turtlePt = new Point(origin.getX()+2, origin.getY()+2);
        if(withinBounds(turtlePt) && !isOccupied(turtlePt)) {
            Turtle newTurtle = createTurtle("turtle", turtlePt, 800, ticks);
            addEntity(newTurtle);
        }
    }

    public boolean checkIce(Point position){
        if(ice.contains(getBackgroundImage(position))){
            Entity bg = background.getCell(position);
            if(bg.getImage() == ice.get(1)){
                Obstacle obstacle = new Obstacle("obstacle", Load.getImages("obstacle"), position);
                addEntity(obstacle);
                return true;

            }
            bg.nextImage();
        }
        return false;
    }

    public void turnBlue(Miner miner){
        for(PImage i : miner.getImages()){
            i.filter(PConstants.GRAY);
        }
        //miner.getImages().filter(PConstants.GRAY);
    }

    public boolean iceContains(Point pt){
        return ice.contains(getBackgroundImage(pt));
    }
}