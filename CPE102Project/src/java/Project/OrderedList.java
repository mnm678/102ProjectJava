package src.java.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marinamoore on 5/17/15.
 */
public class OrderedList {
    public List<ListItem> list;

    OrderedList(){
        this.list = new ArrayList<>();
    }

    public void remove(Actions item){
        int size = list.size();
        int idx = 0;
        while(idx < size && list.get(idx).getItem() != item){
            idx +=1;
        }
        if(idx < size){
            list.remove(idx);
        }
    }

    public void insert(Actions item, long ord){
        int size = list.size();
        int idx = 0;
        while(idx < size && list.get(idx).getOrd() < ord){
            idx +=1;
        }

        list.add(idx, new ListItem(item, ord));
    }

    public ListItem head(){
        if(list.size() > 0){
            return list.get(0);
        }
        else{
            return null;
        }
    }

    public ListItem pop(){
        if(list.size() >0){
            ListItem temp = list.get(0);
            list.remove(list.get(0));
            return temp;
        }
        else{
            return null;
        }
    }
}
