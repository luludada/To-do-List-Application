package test;

import model.Password;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUser {
    User testUser;


    @BeforeEach
    void setUp(){
        testUser = new User("testUser");
    }

    @Test
    void testGetPasswords(){
        assertEquals(0000, testUser.getPasswords().getPassword());
    }

    @Test
    void testSetPasswords(){
        assertEquals(0000, testUser.getPasswords().getPassword());
        Password oneTwoThreeFour = new Password(1234);
        testUser.setPasswords(oneTwoThreeFour);
        assertEquals(1234, testUser.getPasswords().getPassword());
        assertEquals(testUser, oneTwoThreeFour.getUser());
    }

    @Test
    void testGetUserName(){
        assertEquals("testUser", testUser.getUserName());
    }

    @Test
    void testEqual() {
        assertFalse(testUser.equals(null));
        User newUser = new User("newUser");
        assertFalse(testUser.equals(newUser));
        Password testPassword = new Password(1234);
        newUser.setPasswords(testPassword);
        assertFalse(testUser.equals(newUser));
        assertFalse(testUser.equals(testPassword));
        assertFalse(newUser.equals(null));
        assertTrue(newUser.equals(newUser));
    }
}
