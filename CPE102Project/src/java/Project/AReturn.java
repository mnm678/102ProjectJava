package src.java.Project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by marinamoore on 5/25/15.
 */
public class AReturn {
    private Point nextPoint;
    private List<Point> path;
    private HashSet<Point> searched;

    public AReturn(Point nextPoint, List<Point> path, HashSet<Point> searched) {
        this.nextPoint = nextPoint;
        this.path = path;
        this.searched = searched;
    }

    public Point getNextPoint() {
        return nextPoint;
    }

    public List<Point> getPath() {
        return path;
    }

    public HashSet<Point> getSearched() {
        return searched;
    }
}
