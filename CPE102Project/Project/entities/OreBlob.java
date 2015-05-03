package Project.entities;

import Project.Point;

public class OreBlob
extends AnimationRate{
    private int rate;
    public OreBlob(String name, Point position, int rate, int animation_rate){
        super(name, position, animation_rate);
        this.rate = rate;
    }
    public int getRate(){
        return this.rate;
    }
}