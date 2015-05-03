package Project.entities;

import Project.Point;
import Project.Types;

public abstract class Entity
    extends Entities{
    private Point position;
    public Entity(String name, Point position){
        super(name);
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