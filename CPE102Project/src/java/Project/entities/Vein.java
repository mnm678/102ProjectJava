package src.java.Project.entities;

import src.java.Project.Actions;
import src.java.Project.Point;
import src.java.Project.Types;
import processing.core.PImage;
import src.java.Project.WorldModel;

import java.util.List;

public class Vein
extends Animated{

    private int resourceDistance;

    public Vein(String name, List<PImage> imgs, int rate, Point position){
        super(name, imgs, position, rate);
        this.resourceDistance = 1;
    }

    public int getResourceDistance(){
        return this.resourceDistance;
    }

    public Types getType(){
        return Types.VEIN;
    }

    public Actions createVeinAction(WorldModel world){
        Actions [] action = null;
        action[0] = (long currentTicks) ->{
            removePendingAction(action[0]);
            Point openPt = world.findOpenAround(this.getPosition(), this.getResourceDistance());
            if(openPt != null){
                Ore ore = world.createOre("ore -" + this.getName() + " - " + String.valueOf(currentTicks),
                        openPt, currentTicks);
                world.addEntity(ore);
            }

            world.actionScheduleAction(this, this.createVeinAction(world),
                    currentTicks + this.getRate());
        };
        return action[0];
    }

    public void scheduleVein(WorldModel world, long ticks){
        world.actionScheduleAction(this, this.createVeinAction(world),
                ticks + this.getRate());
    }
}