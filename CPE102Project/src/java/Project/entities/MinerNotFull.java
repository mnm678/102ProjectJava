package src.java.Project.entities;

import src.java.Project.Actions;
import src.java.Project.Point;
import processing.core.PImage;
import src.java.Project.Types;
import src.java.Project.WorldModel;

import java.util.List;

public class MinerNotFull
        extends Miner{
    private int resourceCount;
    public MinerNotFull(String name, List<PImage> imgs, int resourceLimit, Point position, int rate, int animationRate){
        super(name, imgs, resourceLimit, position, rate, animationRate);
        this.resourceCount = 0;
    }
    public int getResourceCount(){
        return this.resourceCount;
    }
    public void setResourceCount(int n){
        this.resourceCount = n;
    }

    public Boolean minerToOre(WorldModel world, Ore ore){
        Point minerPt = this.getPosition();
        if(ore == null){
            //System.out.println("null ore");
            return false;
        }
        Point orePt = ore.getPosition();
        System.out.println(minerPt.getX() + " " + minerPt.getY());
        System.out.println(orePt.getX() + " " + orePt.getY());
        if(world.adjacent(minerPt,orePt)){
            //System.out.println("adjacent ore");
            this.setResourceCount(1+this.getResourceCount());
            ore.removeEntity(world);
            return true;
        }
        else{
            Point newPt = world.nextPosition(minerPt,orePt);
            world.moveEntity(this, newPt);
            //System.out.println("hit");
            return false;
        }
    }

    public Miner tryTransformMinerNotFull(WorldModel world){
        if(this.resourceCount < this.getResourceLimit()){
            return this;
        }
        else{
            Miner newEntity = new MinerFull(this.getName(), this.getImages(),
                    this.getResourceLimit(),
                    this.getPosition(), this.getRate(),
                    this.getAnimationRate());
            return newEntity;
        }
    }

    public void scheduleMiner(WorldModel world, long ticks){
        world.actionScheduleAction(this, createMinerAction(world), ticks + this.getRate());
        world.scheduleAnimation(this);
    }

    public Boolean startingAction(Point entityPt, WorldModel world){
        Ore ore = (Ore) world.findNearest(entityPt, Types.ORE);
        //System.out.println("in notFull starting action");
        return minerToOre(world, ore);
    }

    public Miner returnType(WorldModel world){
        return tryTransformMinerNotFull(world);
    }
}