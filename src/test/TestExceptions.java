package test;

import model.*;
import model.exceptions.IncorrectDateFormatException;
import model.exceptions.TooManyItemsException;
import model.exceptions.TooManyUrgentItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class TestExceptions {
    Item academicItem;
    Item regularItem;
    Item urgentItem;
    Item IncorrectItem;
    TodoList testTodoList;

    @BeforeEach
    void setUp(){
        regularItem = new RegularItem("regularItem", "August 30, 2019");
        academicItem = new AcademicItem("AcademicItem", "August 10, 2019");
        urgentItem = new UrgentItem("urgentItem", "August 20, 2019");
        IncorrectItem = new RegularItem("regularItem", "ABCD 30, 2019");
        testTodoList = new TodoList();
    }

    @Test
    void testDateIncorrect() throws TooManyItemsException, TooManyUrgentItemException {
        testTodoList.addItem(IncorrectItem);
        try {
            testTodoList.checkOverDue();
            fail("Incorrect Date");
        } catch (IncorrectDateFormatException e) {
            System.out.println("Excepted");
        }
    }

    @Test
    void testNotTooManyUrgentItem() {
        //Expect not fail since there are not many urgent items and items
        for (int i = 0; i < 5; i++){
            try {
                testTodoList.addItem(urgentItem);
            } catch (TooManyUrgentItemException e) {
                fail("Unexpected exception");
            } catch (TooManyItemsException e) {
                fail("Unexpected exception");
            }
        }
    }

    @Test
    void testTooManyUrgentItem() {
        //Expect not fail since there are not many urgent items and items
        for (int i=0 ; i<5 ; i++) {
            try {
                testTodoList.addItem(urgentItem);
            } catch (TooManyUrgentItemException e) {
                fail("Unexpected exception");
            } catch (TooManyItemsException e) {
                fail("Unexpected exception");
            }
        }

        //Expect fail since there are not too many urgent items but too many items.
        try {
            testTodoList.addItem(urgentItem);
            fail("TooManyUrgentItemException should be thrown");
        } catch (TooManyUrgentItemException e) {
            System.out.println("Expected");
        } catch (TooManyItemsException e) {
            e.printStackTrace();
            fail("Unexpected");
        }
    }

    @Test
    void testTooManyItem() {
        //Expect not fail since there are not many urgent items and items
        for (int i=0 ; i<25 ; i++) {
            try {
                testTodoList.addItem(regularItem);
            } catch (TooManyUrgentItemException e) {
                fail("Unexpected exception");
            } catch (TooManyItemsException e) {
                fail("Unexpected exception");
            }
        }

        //Expect fail since there are too many urgent items but not too many items.
        try {
            testTodoList.addItem(regularItem);
            fail("TooManyItemException should be thrown");
        } catch (TooManyItemsException e) {
            System.out.println("Expected");
        } catch (TooManyUrgentItemException e) {
            fail("Unexpected");
        }
    }

    @Test
    void testTooManyUrgentItemTooManyThings() {
        //Expect not fail since there are not many urgent items and items
        for (int i = 0; i < 5; i++) {
            try {
                testTodoList.addItem(urgentItem);
            } catch (TooManyUrgentItemException e) {
                fail("Unexpected exception");
            } catch (TooManyItemsException e) {
                fail("Unexpected exception");
            }
        }
        //Expect not fail since there are not many urgent items and items
        for (int i = 0; i < 20; i++) {
            try {
                testTodoList.addItem(regularItem);
            } catch (TooManyUrgentItemException e) {
                fail("Unexpected exception");
            } catch (TooManyItemsException e) {
                fail("Unexpected exception");
            }
        }
        //Expect fail since there are too many urgent items but not too many items.
        try {
            testTodoList.addItem(urgentItem);
            fail("TooManyUrgentItem should be thrown");
        } catch (TooManyUrgentItemException e) {
            System.out.println("Excepted");
        } catch (TooManyItemsException e) {
            fail("Unexpected exception");
        }
    }
}
