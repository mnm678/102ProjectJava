package Project.entities;

import Project.Point;

class MinerNotFull
        extends Miner{
    private int resourceCount;
    public MinerNotFull(String name, int resource_limit, Point position, int rate, int animation_rate){
        super(name, resource_limit, position, rate, animation_rate);
        this.resourceCount = 0;
    }
    public int getResourceCount(){
        return this.resourceCount;
    }
    public void setResourceCount(int n){
        this.resourceCount = n;
    }
}