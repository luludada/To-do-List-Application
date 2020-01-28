package test;

import model.*;
import model.exceptions.TooManyItemsException;
import model.exceptions.TooManyUrgentItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSaveable {
    private TodoList todo;
    private UserSystem users;

    @BeforeEach
    void setup() throws IOException {
        todo = new TodoList();
        users = new UserSystem();
        todo.emptyFile("src/testSave");
    }

    @Test
    void testSaveItem() throws IOException, ParseException, TooManyItemsException, TooManyUrgentItemException {
        todo.load("src/testSave");
        assertEquals(0, todo.size());
        todo.addItem(new RegularItem("CPSC 210 Lecture Ticket", "July 20, 2019"));
        todo.addItem(new AcademicItem("CPSC 210 Lecture Ticket", "July 22, 2019"));
        todo.save("src/testSave");
        TodoList newTodo = new TodoList();
        newTodo.load("src/testSave");
        assertEquals(2,todo.size());
    }

    @Test
    void testSaveUser() throws IOException {
        users.load("src/testSave");
        assertEquals(1, users.getUserSystem().size());
        users.addUser("AAA", 000);
        users.addUser("BBB", 111);
        assertEquals(3,users.getUserSystem().size());
        users.save("src/testSave");
        UserSystem newUser = new UserSystem();
        newUser.load("src/testSave");
        assertEquals(3,newUser.getUserSystem().size());
    }

    @Test
    void testItemEmptyFile()
            throws IOException, ParseException, TooManyItemsException, model.exceptions.TooManyUrgentItemException {
        todo.addItem(new RegularItem("CPSC 210 Lecture Ticket", "July 20, 2019"));
        todo.addItem(new UrgentItem("CPSC 210 Lecture Ticket", "July 22, 2019"));
        todo.save("src/testSave");

        TodoList newTodo = new TodoList();
        newTodo.load("src/testSave");
        assertEquals(2,todo.size());

        todo.emptyFile("src/testSave");

        newTodo = new TodoList();
        newTodo.load("src/testSave");
        assertEquals(2,todo.size());
    }


    @Test
    void testUserEmptyFile() throws IOException {
        users.addUser("AAA", 000);
        users.addUser("BBB", 111);
        todo.save("src/testSave");

        UserSystem newUsers = new UserSystem();
        newUsers.load("src/testSave");
        assertEquals(0,todo.size());

        todo.emptyFile("src/testSave");
        assertEquals(3, users.size());

        newUsers = new UserSystem();
        newUsers.load("src/testSave");
        assertEquals(3,users.size());
    }

}
