package Project.entities;

import Project.Point;
import Project.Types;

public class Vein
extends Animated{
    private int resource_distance;
    public Vein(String name, int rate, Point position){
        super(name, position, rate);
        this.resource_distance = 1;
    }
    public int getResourceDistance(){
        return this.resource_distance;
    }

    public Types getType(){
        return Types.VEIN;
    }
}