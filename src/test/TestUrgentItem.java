package test;

import model.UrgentItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUrgentItem extends test.TestItem {
    @BeforeEach
    void setUp(){
        testDate = "07/30/19";
        testName = "CPSC210 Lecture ticket";
        testItem = new UrgentItem(testName, testDate);
    }


    @Test
    void testConstructor(){
        assertEquals(testName, testItem.getName());
        assertEquals(testDate, testItem.getDueDate());
        assertEquals("Urgent", testItem.getType());
        assertEquals("In-progress", testItem.getStatus());
    }

    @Test
    void testToString() {
        assertEquals("CPSC210 Lecture ticket   Status: In-progress   DueDate: 07/30/19   ",
                testItem.toString());
    }
}
