package test;

import model.Item;
import model.RegularItem;
import model.TodoList;
import model.exceptions.IncorrectDateFormatException;
import model.exceptions.TooManyItemsException;
import model.exceptions.TooManyUrgentItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestTodoList {
    TodoList testTodoList;
    private RegularItem testRegularItem1;
    private String testDate1;
    private String testName1;
    private RegularItem testRegularItem2;
    private String testDate2;
    private String testName2;

    @BeforeEach
    void setUp() {
        testTodoList = new TodoList();
        DateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);
        testDate1 = "07/01/19";
        testName1 = "CPSC 210 Lecture ticket";
        testRegularItem1 = new RegularItem(testName1, testDate1);
        testDate2 = "10/20/19";
        testName2 = "CPSC 210 Midterm";
        testRegularItem2 = new RegularItem(testName2, testDate2);
    }

    @Test
    void testAddItemTodoList() throws TooManyItemsException, TooManyUrgentItemException {
        assertEquals(0, testTodoList.size());
        testTodoList.addItem(testRegularItem1);
        assertEquals(1,testTodoList.size());
    }

    @Test
    void testCrossOff() throws TooManyItemsException, TooManyUrgentItemException {
        testTodoList.addItem(testRegularItem1);
        testTodoList.addItem(testRegularItem2);
        assertEquals(2, testTodoList.size());
        assertEquals("In-progress",testTodoList.getItem(0).getStatus());
        testTodoList.crossedOffItem(0);
        assertEquals("Completed",testTodoList.getItem(0).getStatus());
        testTodoList.crossedOffItem(1);
        assertEquals("Completed",testTodoList.getItem(0).getStatus());
        testTodoList.crossedOffItem(2);
        assertEquals("Completed",testTodoList.getItem(0).getStatus());
        testTodoList.crossedOffItem(-1);
        assertEquals("Completed",testTodoList.getItem(0).getStatus());
        assertEquals(2, testTodoList.size());
    }

    @Test
    void tesCrossOffInvalidIndex() throws TooManyItemsException, TooManyUrgentItemException {
        testTodoList.addItem(testRegularItem1);
        assertEquals("In-progress",testTodoList.getItem(0).getStatus());
        testTodoList.crossedOffItem(100);
        assertEquals("In-progress", testTodoList.getItem(0).getStatus());
    }

    @Test
    void testCheckInProgressOneItemOverDue()
            throws IncorrectDateFormatException, TooManyItemsException, TooManyUrgentItemException {
        testTodoList.addItem(testRegularItem1);
        testTodoList.addItem(testRegularItem2);
        assertEquals(2, testTodoList.size());
        assertEquals("In-progress",testTodoList.getItem(0).getStatus());
        assertEquals("In-progress",testTodoList.getItem(1).getStatus());
        testTodoList.checkOverDue();
        assertEquals("Overdue",testTodoList.getItem(0).getStatus());
        assertEquals("In-progress",testTodoList.getItem(1).getStatus());
        testRegularItem1.setDueDate("10/20/19");
        testTodoList.checkInProgress();
        assertEquals("In-progress",testTodoList.getItem(0).getStatus());
        assertEquals("In-progress",testTodoList.getItem(1).getStatus());
    }

    @Test
    void testInprogessException()
            throws IncorrectDateFormatException, TooManyItemsException, TooManyUrgentItemException {
        testTodoList.addItem(testRegularItem1);
        assertEquals(1, testTodoList.size());
        assertEquals("In-progress",testTodoList.getItem(0).getStatus());
        testTodoList.checkOverDue();
        assertEquals("Overdue",testTodoList.getItem(0).getStatus());
        try {
            testRegularItem1.setDueDate("ABC 20, 2019");
            testTodoList.checkInProgress();
            fail("Incorrect Date");
        } catch (IncorrectDateFormatException e) {
            System.out.println("Excepted");
        }
    }

    @Test
    void testCheckInProgressAllItemOverDue()
            throws IncorrectDateFormatException, TooManyItemsException, TooManyUrgentItemException {
        testTodoList.addItem(testRegularItem1);
        testTodoList.addItem(testRegularItem1);
        assertEquals(2, testTodoList.size());
        assertEquals("In-progress",testTodoList.getItem(0).getStatus());
        assertEquals("In-progress",testTodoList.getItem(1).getStatus());
        testTodoList.checkOverDue();
        assertEquals("Overdue",testTodoList.getItem(0).getStatus());
        assertEquals("Overdue",testTodoList.getItem(1).getStatus());
        testRegularItem1.setDueDate("10/20/19");
        testTodoList.checkInProgress();
        assertEquals("In-progress",testTodoList.getItem(0).getStatus());
        assertEquals("In-progress",testTodoList.getItem(1).getStatus());
    }

    @Test
    void testCheckNoInProgress()
            throws IncorrectDateFormatException, TooManyItemsException, TooManyUrgentItemException {
        testTodoList.addItem(testRegularItem1);
        testTodoList.addItem(testRegularItem2);
        assertEquals(2, testTodoList.size());
        assertEquals("In-progress",testTodoList.getItem(0).getStatus());
        assertEquals("In-progress",testTodoList.getItem(1).getStatus());
        testTodoList.crossedOffItem(0);
        testTodoList.crossedOffItem(1);
        assertEquals("Completed",testTodoList.getItem(0).getStatus());
        assertEquals("Completed",testTodoList.getItem(1).getStatus());
        testTodoList.checkInProgress();
        assertEquals("Completed",testTodoList.getItem(0).getStatus());
        assertEquals("Completed",testTodoList.getItem(1).getStatus());
    }


    @Test
    void testCheckOverDueOneItemOverDue()
            throws IncorrectDateFormatException, TooManyItemsException, TooManyUrgentItemException {
        testTodoList.addItem(testRegularItem1);
        testTodoList.addItem(testRegularItem2);
        assertEquals(2, testTodoList.size());
        assertEquals("In-progress",testTodoList.getItem(0).getStatus());
        assertEquals("In-progress",testTodoList.getItem(1).getStatus());
        testTodoList.checkOverDue();
        assertEquals("Overdue",testTodoList.getItem(0).getStatus());
        assertEquals("In-progress",testTodoList.getItem(1).getStatus());
    }

    @Test
    void testCheckOverDueNoItemOverDue()
            throws TooManyItemsException, TooManyUrgentItemException, IncorrectDateFormatException {
        testTodoList.addItem(testRegularItem1);
        testTodoList.addItem(testRegularItem2);
        testTodoList.crossedOffItem(0);
        assertEquals("Completed",testTodoList.getItem(0).getStatus());
        assertEquals("In-progress",testTodoList.getItem(1).getStatus());
        testTodoList.checkOverDue();
        assertEquals("Completed",testTodoList.getItem(0).getStatus());
        assertEquals("In-progress",testTodoList.getItem(1).getStatus());
    }

    @Test
    void testGetTodoList() throws TooManyItemsException, TooManyUrgentItemException {
        List<Item> ItemArray = new ArrayList<>();
        ItemArray.add(testRegularItem1);
        ItemArray.add(testRegularItem2);
        testTodoList.addItem(testRegularItem1);
        testTodoList.addItem(testRegularItem2);
        assertEquals(ItemArray, testTodoList.getTodoList());
    }

}