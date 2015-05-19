package src.java.Project.entities;

import src.java.Project.Actions;
import src.java.Project.Point;
import processing.core.PImage;
import src.java.Project.Types;
import src.java.Project.WorldModel;

import java.util.List;

public class OreBlob
extends AnimationRate{
    private int rate;
    public OreBlob(String name, List<PImage> imgs, Point position, int rate, int animationRate){
        super(name, imgs, position, animationRate);
        this.rate = rate;
    }
    public int getRate(){
        return this.rate;
    }

    public boolean blobToVein(WorldModel world, Vein vein){
        Point blobPt = this.getPosition();
        if(vein.equals(null)){
            return false;
        }
        Point veinPt = vein.getPosition();
        if(world.adjacent(blobPt, veinPt)){
            vein.removeEntity(world);
            return true;
        }
        else{
            Point newPt = this.blobNextPosition(world, veinPt);
            Entity oldEntity = world.getTileOccupant(newPt);
            if(oldEntity instanceof Ore){
                ((Ore) oldEntity).removeEntity(world);
            }
            world.moveEntity(this, newPt);
            return false;
        }
    }

    public Actions createOreBlobAction(WorldModel world){
        Actions [] action = {null};
        action[0] = (long currentTicks) ->{
            removePendingAction(action[0]);

            Point entityPt = this.getPosition();
            Vein vein =  (Vein) world.findNearest(entityPt, Types.VEIN);
            Boolean found = blobToVein(world, vein);

            long nextTime = currentTicks + getRate();
            if(found){
                Quake quake = world.createQuake(vein.getPosition(), currentTicks);
                world.addEntity(quake);
                nextTime = currentTicks + getRate() *2;
            }

            world.actionScheduleAction(this, createOreBlobAction(world), nextTime);
        };
        return action[0];
    }

    public void scheduleBlob(WorldModel world, long ticks){
        world.actionScheduleAction(this, createOreBlobAction(world),
                ticks + this.getRate());
    }

    public Point blobNextPosition(WorldModel world, Point destPt){
        int horiz = world.sign(destPt.getX() - this.getPosition().getX());
        Point newPt = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if(horiz == 0 ||
                (world.isOccupied(newPt) && !(world.getTileOccupant(newPt) instanceof Ore))){
            int vert = world.sign(destPt.getY() - this.getPosition().getY());
            newPt = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if(vert == 0 ||
                    (world.isOccupied(newPt) && !(world.getTileOccupant(newPt) instanceof Ore))){
                newPt = this.getPosition();
            }
        }
        return newPt;
    }
}