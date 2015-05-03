package Project.entities;

import Project.Types;

public abstract class Entities{
    public String name;
    //private int currentImg;
    public Entities(String name){
        this.name = name;
        //this.current_img = 0;
    }

    public abstract Types getType();
}