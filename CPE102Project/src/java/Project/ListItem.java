package src.java.Project;


import javax.swing.*;

/**
 * Created by marinamoore on 5/17/15.
 */
public class ListItem {
    private Actions item;
    private long ord;

    public ListItem(Actions item, long ord){
        this.item = item;
        this.ord = ord;
    }

    public long getOrd(){
        return this.ord;
    }

    public Actions getItem(){
        return this.item;
    }
}
