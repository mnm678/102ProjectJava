package src.java.Project;

/**
 * Created by marinamoore on 5/9/15.
 */
public class Viewport {

    private Point topLeft;
    private int width;
    private int height;

    public Viewport(Point topLeft, int width, int height){

        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point toWorld(Point pt){
        return new Point(pt.getX() + topLeft.getX(), pt.getY() + topLeft.getY());
    }

    public Point fromWorld(Point pt){
        return new Point(pt.getX() - topLeft.getX(), pt.getY() - topLeft.getY());
    }

    public boolean contains(Point pt){
        int x = pt.getX() - topLeft.getX();
        int y = pt.getY() - topLeft.getY();
        return x >= 0 && y >= 0 && x < width && y < height;
    }
}
