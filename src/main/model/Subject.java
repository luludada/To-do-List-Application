package model;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<ItemObserver> itemObservers;

    public Subject() {
        itemObservers = new ArrayList<>();
    }

    public void addObserver(ItemObserver o) {
        itemObservers.add(o);
    }

//    public void removeobserver(ItemObserver o) {
//        itemObservers.remove(o);
//    }

    public void notifyObservers(Item item) {
        for (ItemObserver o: itemObservers) {
            o.update(item);
        }
    }
}
