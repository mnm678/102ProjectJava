package Project;

import Project.Point;
import Project.entities.Entities;

public class Grid{
    private int width;
    private int height;
    private Entities[][] cells;

    public Grid(int width, int height, Entities occupancyValue){
        this.width = width;
        this.height = height;
        cells = new Entities[width][height];

        for(int i=0; i<width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = occupancyValue;
            }
        }
    }

    public void setCell(Point point, Entities value){
        int y= point.getY();
        int x = point.getX();
        this.cells[y][x] = value;
    }

    public Entities getCell(Point point){
        int y= point.getY();
        int x = point.getX();
        return this.cells[y][x];
    }
}