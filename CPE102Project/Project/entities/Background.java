package Project.entities;

import Project.Types;

public class Background
    extends Entities {
    public Background(String name) {
        super(name);
    }

    public Types getType(){
        return Types.ENTITIES;
    }
};