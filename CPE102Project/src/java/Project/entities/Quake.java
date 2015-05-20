package src.java.Project.entities;

import src.java.Project.Actions;
import src.java.Project.Point;
import processing.core.PImage;
import src.java.Project.WorldModel;

import java.util.List;

public class Quake
extends AnimationRate{

    public Quake(String name, List<PImage> imgs, Point position, int animationRate) {
        super(name, imgs, position, animationRate);
    }

    public Actions createEntityDeathAction(){
        Actions [] action = {null};
        action[0] = (long currentTicks) ->{
            this.removePendingAction(action[0]);
            this.removeEntity();
        };
        return action[0];
    }

    public void scheduleQuake(long ticks){
        world.scheduleAnimation(this, world.quakeSteps);
        world.actionScheduleAction(this, this.createEntityDeathAction(),
                ticks + world.quakeDuration);
    }
}