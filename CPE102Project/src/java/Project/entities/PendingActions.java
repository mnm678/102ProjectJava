package src.java.Project.entities;

import src.java.Project.Point;
import processing.core.PImage;

import java.util.List;

public abstract class PendingActions extends InteractiveEntity {
    public PendingActions(String name, List<PImage> imgs, Point position) {
        super(name, imgs, position);
    }
};