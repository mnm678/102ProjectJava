package src.java.Project;

import src.java.Project.entities.Background;
import processing.core.PApplet;
import processing.core.PImage;
import src.java.Project.entities.Entity;

import java.util.List;

/**
 * Created by marinamoore on 5/10/15.
 */
public class Main{

    private static final String defaultImageName = "background_default";


    private WorldView view = WorldView.getInstance();
    private WorldModel world = WorldModel.getInstance();


    public Main(){

    }

    public static void main(String[] args){
        PApplet.main("src.java.Project.Controller");
    }

}
