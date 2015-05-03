package Project.entities;

import Project.Point;

public class MinerFull
        extends Miner{
    private int resourceCount;
    public MinerFull(String name, int resourceLimit, Point position, int rate, int animationRate){
        super(name, resourceLimit, position, rate, animationRate);
        this.resourceCount = resourceLimit;
    }
    public int getResourceCount(){
        return this.resourceCount;
    }
    /*public void setResourceCount(int n){
        this.resourceCount = n;
    }*/
}