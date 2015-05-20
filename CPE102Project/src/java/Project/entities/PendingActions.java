package src.java.Project.entities;

import src.java.Project.Actions;
import src.java.Project.Point;
import processing.core.PImage;
import src.java.Project.WorldModel;

import java.util.ArrayList;
import java.util.List;

public abstract class PendingActions extends InteractiveEntity {

    private List<Actions> pendingActions;

    public PendingActions(String name, List<PImage> imgs, Point position) {
        super(name, imgs, position);
        this.pendingActions = new ArrayList<>();
    }

    public void removePendingAction(Actions action){
        pendingActions.remove(action);
    }

    public void addPendingAction(Actions action){
        pendingActions.add(action);
    }

    public List<Actions> getPendingActions(){
        return pendingActions;
    }

    public void clearPendingActions(){
        pendingActions = new ArrayList<>();
    }

    public void removeEntity(){
        for(Actions action : pendingActions){
            world.unscheduleAction(action);
        }
        this.clearPendingActions();
        world.removeEntity(this);
    }
}