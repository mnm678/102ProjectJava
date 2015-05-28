package src.java.Project.entities;

import src.java.Project.*;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OreBlob
extends AnimationRate{
    private int rate;
    private List<Point> path;
    private HashSet<Point> searched;
    public OreBlob(String name, List<PImage> imgs, Point position, int rate, int animationRate){
        super(name, imgs, position, animationRate);
        this.rate = rate;
    }
    public int getRate(){
        return this.rate;
    }

    public List<Point> getPath() {
        return path;
    }

    public HashSet<Point> getSearched() {
        return searched;
    }

    public boolean blobToVein(Vein vein){
        Point blobPt = this.getPosition();
        if(vein == null){
            path = new ArrayList<>();
            path.add(blobPt);
            searched = new HashSet<>();
            searched.add(blobPt);
            return false;
        }
        Point veinPt = vein.getPosition();
        if(world.adjacent(blobPt, veinPt)){
            vein.removeEntity();
            return true;
        }
        else{
            AReturn A = world.ANextPosition(getPosition(), veinPt);
            Point newPt = A.getNextPoint();
            path = A.getPath();
            searched = A.getSearched();
            Entity oldEntity = world.getTileOccupant(newPt);
            if(oldEntity instanceof Ore){
                ((Ore) oldEntity).removeEntity();
            }
            world.moveEntity(this, newPt);
            return false;
        }
    }

    public Actions createOreBlobAction(){
        Actions [] action = {null};
        action[0] = (long currentTicks) ->{
            removePendingAction(action[0]);

            Point entityPt = this.getPosition();
            Vein vein =  (Vein) world.findNearest(entityPt, Types.VEIN);
            Point vPt = null;
            if (vein != null)
            {
                vPt = vein.getPosition();
            }
            Boolean found = blobToVein(vein);

            long nextTime = currentTicks + getRate();
            if(found){
                Quake quake = world.createQuake(vPt, currentTicks);
                world.removeEntity(vein);
                world.addEntity(quake);
                nextTime = currentTicks + getRate() *2;
            }

            world.actionScheduleAction(this, createOreBlobAction(), nextTime);
        };
        return action[0];
    }

    public void scheduleBlob(long ticks){
        world.actionScheduleAction(this, createOreBlobAction(),
                ticks + this.getRate());
        world.scheduleAnimation(this,0);
    }

    public Types getType(){
        return Types.BLOB;
    }
}