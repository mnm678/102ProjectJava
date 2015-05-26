package src.java.Project;

import java.lang.Math;

public class Point {
    private int x;
    private int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public double distance(Point other){
        return Math.sqrt(Math.pow(this.getX()-other.getX(), 2)+Math.pow(this.getY()-other.getX(),2));
    }

    public double distanceSq(Point other){
        return Math.pow(this.getX()-other.getX(), 2) + Math.pow(this.getY()-other.getY(), 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}