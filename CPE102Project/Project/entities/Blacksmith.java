package Project.entities;

import Project.Point;
import Project.Types;
import Project.entities.Entity;

public class Blacksmith
extends Entity {
    private int rate;
    private int resourceLimit;
    private int resourceDistance;
    private int resourceCount;
    public Blacksmith(String name, Point position, int resourceLimit, int rate){
        super(name, position);
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