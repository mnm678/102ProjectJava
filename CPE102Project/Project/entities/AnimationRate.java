package Project.entities;

import Project.Point;
import Project.entities.PendingActions;

public class AnimationRate
    extends PendingActions {
    private int animation_rate;
    public AnimationRate(String name, Point position, int animation_rate){
        super(name, position);
        this.animation_rate = animation_rate;
    }
    public int getAnimationRate(){
        return this.animation_rate;
    }
}