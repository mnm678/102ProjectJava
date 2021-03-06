package src.java.Project.entities;

import src.java.Project.AReturn;
import src.java.Project.Point;
import processing.core.PImage;
import src.java.Project.Types;
import src.java.Project.WorldModel;

import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MinerFull
        extends Miner{
    private int resourceCount;
    public MinerFull(String name, List<PImage> imgs, List<PImage> grayImage, int resourceLimit, Point position, int rate, int animationRate){
        super(name, imgs, grayImage, resourceLimit, position, rate, animationRate);
        this.resourceCount = resourceLimit;
    }
    public int getResourceCount(){
        return this.resourceCount;
    }
    public void setResourceCount(int n){
        this.resourceCount = n;
    }

    public Boolean startingAction(Point entityPt){
        Blacksmith smith = (Blacksmith) world.findNearest(entityPt, Types.BLACKSMITH);
        return minerToSmith(smith);
    }

    public Miner returnType(){
        return tryTransformMinerFull();
    }

    public Boolean minerToSmith(Blacksmith smith){
        Point minerPt = this.getPosition();
        if(smith.equals(null)){
            List<Point> current = new ArrayList<>();
            current.add(minerPt);
            HashSet<Point> curSearch = new HashSet<>();
            curSearch.add(minerPt);
            this.setPath(current);
            this.setSearched(curSearch);
            return false;
        }
        Point smithPt = smith.getPosition();
        if(world.adjacent(minerPt, smithPt)){
            smith.setResourceCount(smith.getResourceCount() + this.getResourceCount());
            this.setResourceCount(0);
            return true;
        }
        else{
            AReturn A = world.ANextPosition(minerPt, smithPt);
            Point newPt = A.getNextPoint();
            this.setPath(A.getPath());
            this.setSearched(A.getSearched());
            world.moveEntity(this, newPt);
            if(world.iceContains(newPt)){
                this.setImages(grayImages);
            }
            if(!world.iceContains(newPt)){
                this.setImages(originalImages);
            }
            /*boolean obstacle = world.checkIce(getPosition());
            if(obstacle){
                AReturn A2 = world.ANextPosition(minerPt, smithPt);
                Point newPt2 = A.getNextPoint();
                this.setPath(A2.getPath());
                this.setSearched(A2.getSearched());
                world.moveEntity(this, newPt2);
            }*/
            return false;
        }
    }

    public Miner tryTransformMinerFull(){
        Miner newEntity = new MinerNotFull(this.getName(), this.getOriginalImages(), this.getGrayImages(),
                this.getResourceLimit(),
                this.getPosition(), this.getRate(),
                this.getAnimationRate());
        if(world.iceContains(this.getPosition())){
            newEntity.setImages(grayImages);
        }
        return newEntity;

    }

    public void scheduleMiner(long ticks){
        world.actionScheduleAction(this,createMinerAction(),ticks+this.getRate());
        world.scheduleAnimation(this);
    }
}