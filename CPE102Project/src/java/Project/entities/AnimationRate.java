package src.java.Project.entities;

import src.java.Project.Point;
import processing.core.PImage;

import java.util.List;

public class AnimationRate
    extends PendingActions {
    private int animationRate;
    public AnimationRate(String name, List<PImage> imgs, Point position, int animationRate){
        super(name, imgs, position);
        this.animationRate = animationRate;
    }
    public int getAnimationRate(){
        return this.animationRate;
    }
}