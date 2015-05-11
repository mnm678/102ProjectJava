package Project;

import Project.entities.Entities;
import Project.entities.Entity;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.List;

/**
 * Created by marinamoore on 5/8/15.
 */

public class WorldView extends PApple {

    private static WorldView instance = null;
    protected WorldView(){}

    private Viewport viewport;
    private WorldModel world;
    private Controller controller;
    private int tileWidth;
    private int tileHeight;
    private int numRows;
    private int numCols;

    public static WorldView getInstance(){
        if(instance == null){
            instance =  new WorldView();
        }
        return instance;
    }

    public void init(int viewCols, int viewRows,
                     int tileWidth, int tileHeight){
        this.viewport = new Viewport(new Point(0,0),viewCols,viewRows);
        this.world = WorldModel.getInstance();
        this.controller = Controller.getInstance();
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.numRows = world.getNumRows();
        this.numCols = world.getNumCols();
    }

    public void drawBackground(){
        for(int y = 0; y < viewport.getHeight(); y++){
            for(int x = 0; x < viewport.getWidth(); x++){
                Point loc =  new Point(x,y);
                Point wPt = this.viewport.toWorld(loc);
                PImage img = this.world.getBackgroundImage(wPt);
                drawTile(img, loc);
            }
        }
    }

    public void drawEntities(){
        for(Entity entity : world.getEntities()){
            if(this.viewport.contains(entity.getPosition())){
                Point vPt = this.viewport.fromWorld(entity.getPosition());
                controller.image(entity.getImage(), vPt);
            }
        }
    }

    public void drawViewport(){
        this.drawBackground();
        this.drawEntities();
    }

    public void updateView(int deltaX, int deltaY){
        this.viewport = createShiftedViewport(deltaX, deltaY);
        drawViewport();
    }

    public void drawTile(PImage img, Point pt){
        controller.image(img, pt.getX() * tileWidth, pt.getY() * tileHeight);
    }

    public void updateViewTiles(List<Point> tiles){
        for(Point tile : tiles){
            if(this.viewport.contains(tile)){
                Point vPt = viewport.fromWorld(tile);
                PImage img = this.getTileImage(vPt);
                drawTile(img, vPt);
            }
        }
    }

    //updateTile

    public PImage getTileImage(Point viewTilePt){
        Point pt = viewport.toWorld(viewTilePt);
        PImage bgnd = world.getBackgroundImage(pt);
        Entities occupant = world.getTileOccupant(pt);
        PGraphics tile = controller.createGraphics(tileWidth, tileHeight);
        tile.beginDraw();
        tile.image(bgnd,0,0);
        if(world.isOccupied(pt)){
            tile.image(occupant.getImage(),0,0);
        }
        tile.endDraw();
        return tile.get();
    }

    public int clamp(int v, int low, int high){
        return Math.min(high, Math.max(v,low));
    }

    public Viewport createShiftedViewport(int deltaX, int deltaY){
        int newX = clamp(viewport.getTopLeft().getX() + deltaX, 0, numCols - viewport.getWidth());
        int newY = clamp(viewport.getTopLeft().getY() + deltaY, 0, numRows - viewport.getHeight());

        return new Viewport(new Point(newX, newY),this.viewport.getWidth(), this.viewport.getHeight());
    }

}
