package src.java.Project.entities;

import src.java.Project.Point;
import src.java.Project.Types;
import processing.core.PImage;

import java.util.List;

public class Ore
extends Animated{
    public Ore(String name, List<PImage> imgs, Point position, int rate) {
        super(name, imgs, position, rate);
    }

    public Types getType(){
        return Types.ORE;
    }
};