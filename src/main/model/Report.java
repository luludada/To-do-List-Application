package model;

public class Report implements ItemObserver {

    @Override
    public void update(Item i) {
        System.out.println("You have added " + i.getName() + " to the TodoList");
    }
}
