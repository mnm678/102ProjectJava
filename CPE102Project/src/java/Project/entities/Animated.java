package src.java.Project.entities;

import src.java.Project.Point;
import processing.core.PImage;

import java.util.List;

public class Animated
    extends PendingActions {
    private int rate;
    public Animated(String name, List<PImage> imgs, Point position, int rate){
        super(name, imgs, position);
        this.rate =  rate;
    }
    public int getRate(){
        return this.rate;
    }
    public String getName(){
        return this.name;
    }
}