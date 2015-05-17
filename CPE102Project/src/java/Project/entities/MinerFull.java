package src.java.Project.entities;

import src.java.Project.Point;
import processing.core.PImage;

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
    /*public void setResourceCount(int n){
        this.resourceCount = n;
    }*/
}