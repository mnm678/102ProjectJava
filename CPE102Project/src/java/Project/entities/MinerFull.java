package src.java.Project.entities;

import src.java.Project.Point;
import processing.core.PImage;
import src.java.Project.Types;
import src.java.Project.WorldModel;

import java.lang.invoke.WrongMethodTypeException;
import java.util.List;

public class MinerFull
        extends Miner{
    private int resourceCount;
    public MinerFull(String name, List<PImage> imgs, int resourceLimit, Point position, int rate, int animationRate){
        super(name, imgs, resourceLimit, position, rate, animationRate);
        this.resourceCount = resourceLimit;
    }
    public int getResourceCount(){
        return this.resourceCount;
    }
    public void setResourceCount(int n){
        this.resourceCount = n;
    }

    public Boolean startingAction(Point entityPt, WorldModel world){
        Blacksmith smith = (Blacksmith) world.findNearest(entityPt, Types.ORE);
        return minerToSmith(world, smith);
    }

    public Miner returnType(WorldModel world){
        return tryTransformMinerFull(world);
    }
    public Boolean minerToSmith(WorldModel world, Blacksmith smith){
        Point minerPt = this.getPosition();
        if(smith.equals(null)){
            return false;
        }
        Point smithPt = smith.getPosition();
        if(world.adjacent(minerPt,smithPt)){
            smith.setResourceCount(smith.getResourceCount() + this.getResourceCount());
            this.setResourceCount(0);
            return true;
        }
        else{
            Point newPt = world.nextPosition(minerPt,smithPt);
            return false;
        }
    }

    public Miner tryTransformMinerFull(WorldModel world){
        Miner newEntity = new MinerNotFull(this.getName(), this.getImages(),
                this.getResourceLimit(),
                this.getPosition(), this.getRate(),
                this.getAnimationRate());
        return newEntity;

    }

    public void scheduleMiner(WorldModel world, long ticks){
        world.actionScheduleAction(this,createMinerAction(world),ticks+this.getRate());
        world.scheduleAnimation(this);
    }
}