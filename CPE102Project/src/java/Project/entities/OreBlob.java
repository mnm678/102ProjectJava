package src.java.Project.entities;

import src.java.Project.Point;
import processing.core.PImage;

import java.util.List;

public class OreBlob
extends AnimationRate{
    private int rate;
    public OreBlob(String name, List<PImage> imgs, Point position, int rate, int animationRate){
        super(name, imgs, position, animationRate);
        this.rate = rate;
    }
    public int getRate(){
        return this.rate;
    }
}