package src.java.Project.entities;

import src.java.Project.*;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MinerNotFull
        extends Miner{
    private int resourceCount;
    public MinerNotFull(String name, List<PImage> imgs, List<PImage> grayImage, int resourceLimit, Point position, int rate, int animationRate){
        super(name, imgs, grayImage, resourceLimit, position, rate, animationRate);
        this.resourceCount = 0;
    }
    public int getResourceCount(){
        return this.resourceCount;
    }
    public void setResourceCount(int n){
        this.resourceCount = n;
    }

    public Boolean minerToOre(Ore ore){
        Point minerPt = this.getPosition();
        if(ore == null){
            List<Point> current = new ArrayList<>();
            current.add(minerPt);
            HashSet<Point> curSearch = new HashSet<>();
            curSearch.add(minerPt);
            this.setPath(current);
            this.setSearched(curSearch);
            return false;
        }
        Point orePt = ore.getPosition();
        if(world.adjacent(minerPt,orePt)){
            this.setResourceCount(1+this.getResourceCount());
            ore.removeEntity();
            return true;
        }
        else{
            AReturn A = world.ANextPosition(minerPt, orePt);
            Point newPt = A.getNextPoint();
            this.setPath(A.getPath());
            this.setSearched(A.getSearched());
            world.moveEntity(this, newPt);
            if(world.ice.contains(world.getBackgroundImage(newPt))){
                this.setImages(grayImages);
            }
            else{
                this.setImages(originalImages);
            }
            /*boolean obstacle = world.checkIce(getPosition());
            if(obstacle){
                AReturn A2 = world.ANextPosition(minerPt, orePt);
                Point newPt2 = A.getNextPoint();
                this.setPath(A2.getPath());
                this.setSearched(A2.getSearched());
                world.moveEntity(this, newPt2);
            }*/
            return false;
        }
    }

    public Miner tryTransformMinerNotFull(){
        if(this.resourceCount < this.getResourceLimit()){
            return this;
        }
        else{
            Miner newEntity = new MinerFull(this.getName(), this.getOriginalImages(), this.getGrayImages(),
                    this.getResourceLimit(),
                    this.getPosition(), this.getRate(),
                    this.getAnimationRate());
            if(world.iceContains(this.getPosition())){
                newEntity.setImages(grayImages);
            }
            return newEntity;
        }
    }

    public void scheduleMiner(long ticks){
        world.actionScheduleAction(this, createMinerAction(), ticks + this.getRate());
        world.scheduleAnimation(this);
    }

    public Boolean startingAction(Point entityPt){
        Ore ore = (Ore) world.findNearest(entityPt, Types.ORE);
        return minerToOre(ore);
    }

    public Miner returnType(){
        return tryTransformMinerNotFull();
    }
}