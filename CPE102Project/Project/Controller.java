package Project;

import processing.core.*;

import java.util.List;

/**
 * Created by marinamoore on 5/10/15.
 */
public class Controller extends PApplet{

    private static Controller instance = null;
    protected Controller(){}

    WorldView view;
    WorldModel world;

    public static Controller getInstance(){
        if(instance == null){
            instance =  new Controller();
        }
        return instance;
    }

    public void init(){
        this.view = WorldView.getInstance();
        this.world = WorldModel.getInstance();
        activityLoop();
    }

    public void keyPressed(){
        int deltaX = 0;
        int deltaY = 0;
        switch (keyCode){
            case UP:
                deltaY --;
                break;
            case DOWN:
                deltaY ++;
                break;
            case LEFT:
                deltaX --;
                break;
            case RIGHT:
                deltaX++;
                break;
        }
        view.updateView(deltaX, deltaY);
    }

    public void handleTimerEvent(){
        List<Point> rects = world.updateOnTime();
        view.updateViewTiles(rects);
    }

    public void activityLoop(){

    }
}
