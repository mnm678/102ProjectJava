package src.java.Project;

import processing.core.*;
import src.java.Project.entities.Background;

/**
 * Created by marinamoore on 5/10/15.
 */
public class Controller extends PApplet{

    private WorldView view = WorldView.getInstance();
    private WorldModel world = WorldModel.getInstance();
    private Load load = Load.getInstance();

    private long nextTime;

    private final int worldWidthScale = 2;
    private final int worldHeightScale = 2;
    private final int screenWidth = 640;
    private final int screenHeight = 480;
    private final int tileWidth = 32;
    private final int tileHeight = 32;
    private final int animationTime = 100;

    private static final int COLOR_MASK = 0xffffff;

    private PImage test;

    private int numCols = (screenWidth / tileWidth) * worldWidthScale;
    private int numRows = (screenHeight / tileHeight) * worldHeightScale;

    private final String worldFile = "src/java/Project/gaia.sav";
    private final String imageListFileName = "src/java/Project/imagelist";

    private final String defaultImageName = "background_default";


    public Controller() {
        load.init(this);
    }

    public void setup(){
        size(screenWidth, screenHeight);

        Background defaultBackground = new Background(defaultImageName, load.getImages(defaultImageName));
        world.init(numRows, numCols, defaultBackground);

        nextTime = System.currentTimeMillis() + animationTime;


        load.loadImages(imageListFileName);

        Load.loadWorld(worldFile, true, System.currentTimeMillis());
        view.init(screenWidth / tileWidth, screenHeight / tileHeight, tileWidth, tileHeight, this);
        view.drawViewport();
        test = setAlpha(loadImage("miner1.bmp"), color(255, 255, 255), 0);
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

    public void handleTimerEvent(){
        world.updateOnTime(System.currentTimeMillis());
    }

    public void draw(){

        long time = System.currentTimeMillis();
        if(time >= nextTime){
            view.drawViewport();
            handleTimerEvent();
            nextTime = System.currentTimeMillis() + 100;
        }
    }

    public static PImage setAlpha(PImage img, int maskColor, int alpha)
    {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++)
        {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
            {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
        return img;
    }
}
