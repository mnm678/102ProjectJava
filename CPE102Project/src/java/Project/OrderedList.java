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
        while(idx < size && list.get(idx) != item){
            idx +=1;
        }
        if(idx < size){
            list.set(idx, null);
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
        if(list != null){
            return list.get(0);
        }
        else{
            return null;
        }
    }

    public ListItem pop(){
        if(list != null){
            ListItem temp = list.get(0);
            list.remove(list.get(0));
            return temp;
        }
        else{
            return null;
        }
    }
}
