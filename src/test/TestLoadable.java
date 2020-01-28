package test;

import model.TodoList;
import model.UserSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLoadable {
    private TodoList todo;
    private UserSystem users;


    @BeforeEach
    void setup() {
        todo = new TodoList();
        users = new UserSystem();
    }

    @Test
    void testLoadFromFile() throws IOException {
        assertEquals(0, todo.size());
        todo.load("src/testLoad");
        assertEquals(3, todo.size());
        assertEquals("CPSC 210 Midterm",todo.getItem(0).getName());
        assertEquals("CPSC 210 Lecture Ticket",todo.getItem(1).getName());
        assertEquals("Build project", todo.getItem(2).getName());
    }

    @Test
    void testLoadUserSystem() throws IOException {
        assertEquals(1, users.getUserSystem().size());
        users.load("src/testLoadUsers");
        assertEquals(1, users.getUserSystem().size());
    }
}
