package Project.entities;

import Project.Point;
import Project.Types;

public class Vein
extends Animated{
    private int resourceDistance;
    public Vein(String name, int rate, Point position){
        super(name, position, rate);
        this.resourceDistance = 1;
    }
    public int getResourceDistance(){
        return this.resourceDistance;
    }

    public Types getType(){
        return Types.VEIN;
    }
}