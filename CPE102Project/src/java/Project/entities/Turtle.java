package src.java.Project.entities;

import processing.core.PImage;
import src.java.Project.AReturn;
import src.java.Project.Actions;
import src.java.Project.Point;
import src.java.Project.Types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by marinamoore on 6/3/15.
 */
public class Turtle extends AnimationRate{
    private int rate;
    private List<Point> path;
    private HashSet<Point> searched;
    public Turtle(String name, List<PImage> imgs, Point position, int rate, int animationRate){
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

    public boolean turtleToBlob(OreBlob blob){
        Point turtlePt = this.getPosition();
        if(blob == null){
            path = new ArrayList<>();
            path.add(turtlePt);
            searched = new HashSet<>();
            searched.add(turtlePt);
            return false;
        }
        Point blobPt = blob.getPosition();
        if(world.adjacent(turtlePt, blobPt)){
            blob.removeEntity();
            return true;
        }
        else{
            AReturn A = world.ANextPosition(getPosition(), blobPt);
            Point newPt = A.getNextPoint();
            path = A.getPath();
            searched = A.getSearched();
            Entity oldEntity = world.getTileOccupant(newPt);
            if(oldEntity instanceof OreBlob){
                ((OreBlob) oldEntity).removeEntity();
            }
            world.moveEntity(this, newPt);
            return false;
        }
    }

    public Actions createTurtleAction(){
        Actions [] action = {null};
        action[0] = (long currentTicks) ->{
            removePendingAction(action[0]);

            Point entityPt = this.getPosition();
            OreBlob blob =  (OreBlob) world.findNearest(entityPt, Types.BLOB);
            Point vPt = null;
            if (blob != null)
            {
                vPt = blob.getPosition();
            }
            Boolean found = turtleToBlob(blob);

            long nextTime = currentTicks + getRate();
            if(found){
                Quake quake = world.createQuake(vPt, currentTicks);
                world.removeEntity(blob);
                world.addEntity(quake);
                nextTime = currentTicks + getRate() *2;
            }

            world.actionScheduleAction(this, createTurtleAction(), nextTime);
        };
        return action[0];
    }

    public void scheduleTurtle(long ticks){
        world.actionScheduleAction(this, createTurtleAction(),
                ticks + this.getRate());
        world.scheduleAnimation(this,0);
    }

    public Types getType(){
        return Types.TURTLE;
    }
}
