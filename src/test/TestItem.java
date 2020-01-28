package test;

import model.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class TestItem {
    protected Item testItem;
    protected String testDate;
    protected String testName;
    protected String testType;

    @Test
    void testsetName(){
        assertEquals(testName, testItem.getName());
        testItem.setName("CPSC 210 Lecture Ticket");
        assertEquals("CPSC 210 Lecture Ticket", testItem.getName());
    }

    @Test
    void testsetDate() {
        assertEquals(testDate, testItem.getDueDate());
        String newDate = "July 30, 2019";
        testItem.setDueDate(newDate);
        assertEquals(newDate, testItem.getDueDate());
    }


    @Test
    void testsetStatus(){
        assertEquals("In-progress", testItem.getStatus());
        testItem.setStatus("Completed");
        assertEquals("Completed", testItem.getStatus());
    }
}
