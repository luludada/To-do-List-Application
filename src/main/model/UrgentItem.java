package model;

public class UrgentItem extends Item {
    public UrgentItem(String name, String dueDate) {
        super(name, dueDate);
    }

    UrgentItem(String name, String status, String dueDate) {
        super(name, status, dueDate);
    }

    @Override
    public String getType() {
        return "Urgent";
    }

    @Override
    public String toString() {
        return name + "   " + "Status: " + status + "   " + "DueDate: " + dueDate + "  " + " ";
    }
}
