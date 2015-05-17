package src.java.Project.entities;

import src.java.Project.Point;
import src.java.Project.Types;
import processing.core.PImage;

import java.util.List;

public class Vein
extends Animated{
    private int resourceDistance;
    public Vein(String name, List<PImage> imgs, int rate, Point position){
        super(name, imgs, position, rate);
        this.resourceDistance = 1;
    }
    public int getResourceDistance(){
        return this.resourceDistance;
    }

    public Types getType(){
        return Types.VEIN;
    }
}