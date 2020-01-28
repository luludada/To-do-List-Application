import model.Password;
import model.User;
import model.UserSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUserSystem {

    private UserSystem userSystem;
    private User admin = new User("admin");

    @BeforeEach
    void setUp(){
        userSystem = new UserSystem();
    }

    @Test
    void testdefaultUser() {
        userSystem.defaultUser();
        assertTrue(userSystem.getUserSystem().containsKey(admin));
    }

    @Test
    void testVerification() throws IOException {
        //Test loadUser
        userSystem.load("src/testLoadUsers");
        assertTrue(userSystem.verification(000, "AAA"));
        //add new user
        User u1 = new User("CCC", new Password(222));
        userSystem.addUser(u1);
        assertTrue(userSystem.verification(222, "CCC"));
        assertFalse(userSystem.verification(111, "abc"));
    }

    @Test
    void testResetPasswords(){
        User u1 = new User("CCC", new Password(222));
        userSystem.addUser(u1);
        assertTrue(userSystem.verification(222, "CCC"));
        userSystem.resetPasswords(u1,111);
        assertFalse(userSystem.verification(222, "abc"));
        assertFalse(userSystem.verification(000, "CCC"));
        assertTrue(userSystem.verification(111, "CCC"));
    }

    @Test
    void testAddUser(){
        assertEquals(1, userSystem.getUserSystem().size());
        userSystem.addUser("CCC", 111);
        assertEquals(2, userSystem.getUserSystem().size());
        User u1 = new User("DDD");
        userSystem.addUser(u1);
        assertEquals(3, userSystem.getUserSystem().size());
    }

    @Test
    void testEmptyFile() throws IOException {
        assertTrue(userSystem.getUserSystem().containsKey(admin));
        assertEquals(1, userSystem.getUserSystem().size());
        userSystem.addUser("testUser", 111);
        userSystem.emptyFile("userFile");
    }

    @Test
    void testSave() throws IOException {
        userSystem.load("src/testLoadUsers");
        assertEquals(1, userSystem.getUserSystem().size());
        userSystem.addUser("AAA", 000);
        userSystem.addUser("BBB", 111);
        assertEquals(3, userSystem.getUserSystem().size());
        userSystem.save("src/testLoadUsers");
        UserSystem newUserSystem = new UserSystem();
        newUserSystem.load("src/testLoadUsers");
        assertEquals(3, newUserSystem.getUserSystem().size() );


    }
}
