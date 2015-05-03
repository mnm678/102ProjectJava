package Project.entities;

import Project.Point;

class Miner
    extends Animated{
    private int resourceLimit;
    private int animationRate;
    private int resourceCount;
    public Miner(String name, int resourceLimit, Point position, int rate, int animationRate){
        super(name, position, rate);
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