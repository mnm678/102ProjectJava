package src.java.Project.entities;

import src.java.Project.Point;
import src.java.Project.Types;
import processing.core.PImage;

import java.util.List;

public abstract class InteractiveEntity
    extends Entity {
    private Point position;
    public InteractiveEntity(String name, List<PImage> imgs, Point position){
        super(name,imgs);
        this.position = position;
    }
    public void setPosition(Point point){
        this.position = point;
    }
    public Point getPosition() {
        return this.position;
    }

    public Types getType(){
        return Types.ENTITIES;
    }
}