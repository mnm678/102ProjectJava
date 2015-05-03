package Project.entities;

import Project.Point;
import Project.entities.PendingActions;

public class AnimationRate
    extends PendingActions {
    private int animationRate;
    public AnimationRate(String name, Point position, int animationRate){
        super(name, position);
        this.animationRate = animationRate;
    }
    public int getAnimationRate(){
        return this.animationRate;
    }
}