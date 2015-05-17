package src.java.Project;

import processing.core.*;
import src.java.Project.entities.Background;
import src.java.Project.entities.Entity;

import java.awt.*;

/**
 * Created by marinamoore on 5/10/15.
 */
public class Controller extends PApplet{

    private WorldView view = WorldView.getInstance();
    private WorldModel world = WorldModel.getInstance();
    private Load load = Load.getInstance();

    private final int worldWidthScale = 2;
    private final int worldHeightScale = 2;
    private final int screenWidth = 640;
    private final int screenHeight = 480;
    private final int tileWidth = 32;
    private final int tileHeight = 32;

    private PImage test;

    private int numCols = (screenWidth / tileWidth) * worldWidthScale;
    private int numRows = (screenHeight / tileHeight) * worldHeightScale;

    private final String worldFile = "src/java/Project/gaia.sav";
    private final String imageListFileName = "src/java/Project/imagelist";

    private final String defaultImageName = "background_default";
    //private Entity defaultBackground = Main.createDefaultBackground(getImages(defaultImageName));


    public Controller() {
        //view.init(screenWidth / tileWidth, screenHeight / tileHeight, tileWidth, tileHeight, this);
        load.init(this);
    }

    public void setup(){
        size(screenWidth, screenHeight);

        Background defaultBackground = new Background(defaultImageName, load.getImages(defaultImageName));
        world.init(numRows, numCols, defaultBackground);

        //PImage test = loadImage("grass.bmp");
        load.loadImages(imageListFileName);
        //PImage grass = load.getImages("grass").get(0);
        //Load.testDraw();
        //image(grass, 32, 0);
        //view.drawTile(test, new Point(2, 2));

        Load.loadWorld(worldFile, true, world);
        view.init(screenWidth / tileWidth, screenHeight / tileHeight, tileWidth, tileHeight, this);
        view.drawViewport();
        System.out.println(world.getBackgroundImage(new Point(20, 15)));
        System.out.println(world.getBackgroundImage(new Point(35,15)));
        System.out.println(world.getNumCols());
        System.out.println(world.getNumRows());
    }


    public void keyPressed(){
        int deltaX = 0;
        int deltaY = 0;
        switch (keyCode){
            case UP:
                deltaY--;
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

    /*public void handleTimerEvent(){
        List<Point> rects = world.updateOnTime();
        view.updateViewTiles(rects);
    }*/

    public void draw(){
        view.drawViewport();
    }
}
