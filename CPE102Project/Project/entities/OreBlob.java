package Project.entities;

import Project.Point;

public class OreBlob
extends AnimationRate{
    private int rate;
    public OreBlob(String name, Point position, int rate, int animationRate){
        super(name, position, animationRate);
        this.rate = rate;
    }
    public int getRate(){
        return this.rate;
    }
}