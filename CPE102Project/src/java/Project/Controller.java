package src.java.Project;

import processing.core.*;
import src.java.Project.entities.*;

import java.util.ArrayList;
import java.util.List;

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

    private int numCols = (screenWidth / tileWidth) * worldWidthScale;
    private int numRows = (screenHeight / tileHeight) * worldHeightScale;

    private final String worldFile = "src/java/Project/gaia.sav";
    private final String imageListFileName = "src/java/Project/imagelist";

    private final String defaultImageName = "background_default";

    private PImage pathImage;
    private PImage searchImage;
    private PImage ice;
    private PImage ice2;


    public Controller() {
        load.init(this);
    }

    public void setup(){
        size(screenWidth, screenHeight);

        ice = loadImage("ice.png");
        ice2 = loadImage("ice2.png");
        List<PImage> iceList = new ArrayList<>();
        iceList.add(ice);
        iceList.add(ice2);

        Background defaultBackground = new Background(defaultImageName, load.getImages(defaultImageName));
        world.init(numRows, numCols, defaultBackground, iceList);

        nextTime = System.currentTimeMillis() + animationTime;

        pathImage = loadImage("redTile.png");
        searchImage = loadImage("blackTile.png");

        load.loadImages(imageListFileName);

        Load.loadWorld(worldFile, true, System.currentTimeMillis());
        view.init(screenWidth / tileWidth, screenHeight / tileHeight, tileWidth, tileHeight, this);
        view.drawViewport();
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

    public void mousePressed(){
        Point mouseLoc = view.getViewport().toWorld(new Point(mouseX / 32, mouseY / 32));
        world.worldEvent(mouseLoc, System.currentTimeMillis());
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

        Point mouseLoc = view.getViewport().toWorld(new Point(mouseX / 32, mouseY / 32));
        Entity occupant = world.getTileOccupant(mouseLoc);
        if(occupant != null && occupant.getType() == Types.MINER){
            Miner miner = (Miner) occupant;
            if(miner.getPath() != null) {
                for (Point p : miner.getSearched()){
                    drawPath(p, searchImage);
                }
                for (Point p : miner.getPath()) {
                    drawPath(p, pathImage);
                }
            }
        }
        if(occupant != null && occupant.getType() == Types.BLOB){
            OreBlob blob = (OreBlob) occupant;
            if(blob.getPath() != null) {
                for (Point p : blob.getSearched()){
                    drawPath(p, searchImage);
                }
                for (Point p : blob.getPath()) {
                    drawPath(p, pathImage);
                }
            }
        }

        if(occupant != null && occupant.getType() == Types.TURTLE){
            Turtle blob = (Turtle) occupant;
            if(blob.getPath() != null) {
                for (Point p : blob.getSearched()){
                    drawPath(p, searchImage);
                }
                for (Point p : blob.getPath()) {
                    drawPath(p, pathImage);
                }
            }
        }
    }

    public void drawPath(Point p, PImage image){
        Point newP = view.getViewport().fromWorld(p);
        view.drawTile(image, newP);
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
