package Project.entities;

import Project.Point;
import Project.entities.PendingActions;

class Animated
    extends PendingActions {
    private int rate;
    public Animated(String name, Point position, int rate){
        super(name, position);
        this.rate =  rate;
    }
    public int getRate(){
        return this.rate;
    }
    public String getName(){
        return this.name;
    }
}