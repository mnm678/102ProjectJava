package Project.entities;

import Project.Point;
import Project.Types;

public class Ore
extends Animated{
    public Ore(String name, Point position, int rate) {
        super(name, position, rate);
    }

    public Types getType(){
        return Types.ORE;
    }
};