package src.java.Project.entities;

import src.java.Project.Point;
import processing.core.PImage;

import java.util.List;

class Quake
extends AnimationRate{

    public Quake(String name, List<PImage> imgs, Point position, int animationRate) {
        super(name, imgs, position, animationRate);
    }
};