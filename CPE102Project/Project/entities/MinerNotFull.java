package Project.entities;

import Project.Point;

class MinerNotFull
        extends Miner{
    private int resourceCount;
    public MinerNotFull(String name, int resourceLimit, Point position, int rate, int animationRate){
        super(name, resourceLimit, position, rate, animationRate);
        this.resourceCount = 0;
    }
    public int getResourceCount(){
        return this.resourceCount;
    }
    public void setResourceCount(int n){
        this.resourceCount = n;
    }
}