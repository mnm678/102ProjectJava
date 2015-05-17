package src.java.Project.entities;

import src.java.Project.Types;
import processing.core.PImage;

import java.util.List;

public class Background
    extends Entity {
    public Background(String name, List<PImage> imgs) {
        super(name, imgs);
    }

    public Types getType(){
        return Types.ENTITIES;
    }
};