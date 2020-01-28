package model;

public class AcademicItem extends Item {

    public AcademicItem(String name, String dueDate) {
        super(name, dueDate);
    }

    AcademicItem(String name, String status, String dueDate) {
        super(name, status, dueDate);
    }

    @Override
    public String getType() {
        return "Academic";
    }

    @Override
    public String toString() {
        return name + "   " + "Status: " + status + "   " + "DueDate: " + dueDate + "  ";
    }

}
