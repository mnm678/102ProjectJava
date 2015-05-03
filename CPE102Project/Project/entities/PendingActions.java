package Project.entities;

import Project.Point;
import Project.Types;
import Project.entities.Entity;

public abstract class PendingActions extends Entity {
    public PendingActions(String name, Point position) {
        super(name, position);
    }
};