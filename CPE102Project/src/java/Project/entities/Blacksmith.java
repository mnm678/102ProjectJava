package src.java.Project.entities;

import src.java.Project.Point;
import src.java.Project.Types;
import processing.core.PImage;

import java.util.List;

public class Blacksmith
extends InteractiveEntity {
    private int rate;
    private int resourceLimit;
    private int resourceDistance;
    private int resourceCount;
    public Blacksmith(String name, List<PImage> imgs, Point position, int resourceLimit, int rate){
        super(name, imgs, position);
        this.resourceLimit =resourceLimit;
        this.resourceCount = 0;
        this.rate = rate;
        this.resourceDistance = 1;
    }
    public int getRate(){
        return this.rate;
    }
    public int getResourceCount(){
        return this.resourceCount;
    }
    public void setResourceCount(int n){
        this.resourceCount = n;
    }

    public Types getType(){
        return Types.BLACKSMITH;
    }
}