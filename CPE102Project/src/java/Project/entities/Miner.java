package src.java.Project.entities;

import src.java.Project.Point;
import processing.core.PImage;

import java.util.List;

public class Miner
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
}