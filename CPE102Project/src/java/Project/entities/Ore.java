package src.java.Project.entities;

import src.java.Project.Actions;
import src.java.Project.Point;
import src.java.Project.Types;
import processing.core.PImage;
import src.java.Project.WorldModel;

import java.sql.Blob;
import java.util.List;

public class Ore
extends Animated{
    public Ore(String name, List<PImage> imgs, Point position, int rate) {
        super(name, imgs, position, rate);
    }

    public Types getType(){
        return Types.ORE;
    }

    public Actions createOreTransformAction(){
        Actions [] action = {null};
        action[0] = (long currentTicks) ->{
            removePendingAction(action[0]);
            OreBlob blob = world.createBlob(this.getName() + " -- blob",
                    this.getPosition(),
                    this.getRate()/world.blobRateScale,
                    currentTicks);
            this.removeEntity();
            world.addEntity(blob);
        };
        return action[0];
    }

    public void scheduleOre(long ticks){
        world.actionScheduleAction(this, createOreTransformAction(),
                ticks + this.getRate());
    }
};