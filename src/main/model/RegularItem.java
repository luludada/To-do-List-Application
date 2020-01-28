package model;

public class RegularItem extends Item {
    public RegularItem(String name, String dueDate) {
        super(name, dueDate);
    }

    RegularItem(String name, String status, String dueDate) {
        super(name, status, dueDate);
    }

    @Override
    public String getType() {
        return "Regular";
    }

    @Override
    public String toString() {
        return name + "   " + "Status: " + status + "   " + "DueDate: " + dueDate + "  ";
    }
}
