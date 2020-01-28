package Test;

import model.AcademicItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestAcademicItem extends test.TestItem {
    @BeforeEach
    void setUp(){
        testDate = "07/08/17";
        testName = "CPSC210 Lecture ticket";
        testItem = new AcademicItem(testName, testDate);
    }


    @Test
    void testConstructor(){
        assertEquals(testName, testItem.getName());
        assertEquals(testDate, testItem.getDueDate());
        assertEquals("Academic", testItem.getType());
        assertEquals("In-progress", testItem.getStatus());
    }

    @Test
    void testToString() {
        assertEquals("CPSC210 Lecture ticket   Status: In-progress   DueDate: 07/08/17  ",
                testItem.toString());
    }

}
