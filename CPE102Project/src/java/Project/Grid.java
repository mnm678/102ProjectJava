package src.java.Project;

import src.java.Project.entities.Entity;

public class Grid{
    private int width;
    private int height;
    private Entity[][] cells;

    public Grid(int width, int height, Entity occupancyValue){
        this.width = width;
        this.height = height;
        cells = new Entity[height][width];

        for(int i=0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[j][i] = occupancyValue;
            }
        }
    }

    public void setCell(Point point, Entity value){
        int y= point.getY();
        int x = point.getX();
        this.cells[y][x] = value;
    }

    public Entity getCell(Point point){
        int y= point.getY();
        int x = point.getX();
        return this.cells[y][x];
    }
}