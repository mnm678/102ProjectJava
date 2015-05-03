package Project.entities;

import Project.Point;
import Project.Types;
import Project.entities.Entity;

public class Blacksmith
extends Entity {
    private int rate;
    private int resource_limit;
    private int resource_distance;
    private int resource_count;
    public Blacksmith(String name, Point position, int resource_limit, int rate){
        super(name, position);
        this.resource_limit =resource_limit;
        this.resource_count = 0;
        this.rate = rate;
        this.resource_distance = 1;
    }
    public int getRate(){
        return this.rate;
    }
    public int getResourceCount(){
        return this.resource_count;
    }
    public void setResourceCount(int n){
        this.resource_count = n;
    }

    public Types getType(){
        return Types.BLACKSMITH;
    }
}