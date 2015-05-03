package Project.entities;

import Project.Point;

public class MinerFull
        extends Miner{
    private int resourceCount;
    public MinerFull(String name, int resource_limit, Point position, int rate, int animation_rate){
        super(name, resource_limit, position, rate, animation_rate);
        this.resourceCount = resource_limit;
    }
    public int getResourceCount(){
        return this.resourceCount;
    }
    /*public void setResourceCount(int n){
        this.resourceCount = n;
    }*/
}