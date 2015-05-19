package src.java.Project.entities;

import src.java.Project.Actions;
import src.java.Project.Point;
import processing.core.PImage;
import src.java.Project.WorldModel;

import java.lang.reflect.Array;
import java.util.List;

public abstract class Miner
    extends Animated{
    private int resourceLimit;
    private int animationRate;
    private int resourceCount;
    public Miner(String name, List<PImage> imgs, int resourceLimit, Point position, int rate, int animationRate){
        super(name, imgs, position, rate);
        this.resourceLimit = resourceLimit;
        //this.resourceCount = 0;
        this.animationRate = animationRate;
    }
    /*public void setResourceCount(int n){
        this.resourceCount = n;
    }*/
    /*public int getResourceCount(){
        return this.resourceCount;
    }*/
    public int getResourceLimit(){
        return this.resourceLimit;
    }
    public int getAnimationRate(){
        return this.animationRate;
    }

    public Miner tryTransformMiner(WorldModel world, Entity transform){
        Miner newEntity = evaluate(world);
        if(this != newEntity){
            world.actionsClearPendingActions(this);
            world.removeEntityAt(this.getPosition());
            world.addEntity(newEntity);
            world.scheduleAnimation(newEntity);
        }
        return newEntity;
    }

    public Actions createMinerAction(WorldModel world){
        Actions [] action = {null};
        action[0] = (long currentTicks) ->{
            //System.out.println("in createMinerAction");
            removePendingAction(action[0]);

            Point entityPt = this.getPosition();
            Boolean found = startingAction(entityPt, world);
            //System.out.println(found);

            Miner newEntity = this;
            if(found){
                newEntity = tryTransformMiner(world, returnType(world));
            }

            world.actionScheduleAction(newEntity, newEntity.createMinerAction(world),
                    currentTicks + newEntity.getRate());
        };
        return action[0];
    }

    public Miner evaluate(WorldModel world){
        return returnType(world);
    }

    abstract Miner returnType(WorldModel world);

    abstract Boolean startingAction(Point pt, WorldModel world);
}