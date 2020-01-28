import model.Password;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPassword {
    private Password testPassword;
    private User user = new User("testUser");

    @BeforeEach
    void setUp() {
        testPassword = new Password(0000);
    }

    @Test
    void testGetPassword() {
        assertEquals(0000, testPassword.getPassword());
    }

    @Test
    void testGetUser() {
        user.setPasswords(testPassword);
        assertEquals(user, testPassword.getUser());
    }

    @Test
    void testSetUser() {
        User user = new User("newUser");
        testPassword.setUser(user);
        assertEquals(testPassword, user.getPasswords());
    }

    @Test
    void testSetStudent() {
        User user = new User("newUser");
        testPassword.setUser(user);
        assertEquals(testPassword, user.getPasswords());
    }

    @Test
    void testEqual() {
        assertFalse(testPassword.equals(null));
        assertFalse(testPassword.equals(user));
        User AAA = new User("AAA", testPassword);
        assertFalse(user.equals(AAA));
        assertFalse(user.equals(null));
        assertTrue(user.equals(user));
        assertTrue(testPassword.equals(testPassword));
    }
}
