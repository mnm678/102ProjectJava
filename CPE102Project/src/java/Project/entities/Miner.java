package src.java.Project.entities;

import processing.core.PConstants;
import src.java.Project.Actions;
import src.java.Project.Point;
import processing.core.PImage;
import src.java.Project.Types;

import java.util.HashSet;
import java.util.List;

public abstract class Miner
    extends Animated{
    private int resourceLimit;
    private int animationRate;
    private List<Point> path;
    private HashSet<Point> searched;
    protected List<PImage> originalImages;
    protected List<PImage> grayImages;
    public Miner(String name, List<PImage> imgs, List<PImage> grayImages, int resourceLimit, Point position, int rate, int animationRate){
        super(name, imgs, position, rate);
        this.resourceLimit = resourceLimit;
        this.animationRate = animationRate;
        this.originalImages = imgs;
        this.grayImages = grayImages;
    }

    public List<PImage> getGrayImages() {
        return grayImages;
    }

    public HashSet<Point> getSearched() {
        return searched;
    }

    public void setSearched(HashSet<Point> searched) {
        this.searched = searched;
    }

    public void setPath(List<Point> path) {
        this.path = path;
    }

    public List<Point> getPath() {
        return path;
    }

    public int getResourceLimit(){
        return this.resourceLimit;
    }
    public int getAnimationRate(){
        return this.animationRate;
    }

    public Miner tryTransformMiner(){
        Miner newEntity = evaluate();
        if(this != newEntity){
            world.actionsClearPendingActions(this);
            world.removeEntityAt(this.getPosition());
            world.addEntity(newEntity);
            world.scheduleAnimation(newEntity);
        }
        return newEntity;
    }

    public Actions createMinerAction(){
        Actions [] action = {null};
        action[0] = (long currentTicks) ->{
            removePendingAction(action[0]);

            Point entityPt = this.getPosition();
            Boolean found = startingAction(entityPt);

            Miner newEntity = this;
            if(found){
                newEntity = tryTransformMiner();
            }

            world.actionScheduleAction(newEntity, newEntity.createMinerAction(),
                    currentTicks + newEntity.getRate());
        };
        return action[0];
    }

    public Miner evaluate(){
        return returnType();
    }

    abstract Miner returnType();

    abstract Boolean startingAction(Point pt);

    public Types getType(){
        return Types.MINER;
    }
}