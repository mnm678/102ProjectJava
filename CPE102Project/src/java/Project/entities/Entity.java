package src.java.Project.entities;

import src.java.Project.Types;
import processing.core.PImage;

import java.util.List;

public abstract class Entity {
    public String name;
    private int currentImg;
    private List<PImage> imgs;
    public Entity(String name, List<PImage> imgs){
        this.name = name;
        this.currentImg = 0;
        this.imgs = imgs;
    }

    public abstract Types getType();

    public List<PImage> getImages(){
        return imgs;
    }

    public PImage getImage(){
        return imgs.get(currentImg);
    }

    public void nextImage(){
        this.currentImg = (currentImg +1) % imgs.size();
    }
}