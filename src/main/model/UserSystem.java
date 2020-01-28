package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class UserSystem implements Loadable, Saveable {
    private Map<User, Password> userSystem;

    public UserSystem() {
        userSystem = new HashMap<>();
        defaultUser();
    }

    // EFFECTS: check if user have access to the todolist, return false if access denied
    public boolean verification(int passWordEntered, String nameEntered) {
        Password passwordEntered = new Password(passWordEntered);
        User userEntered = new User(nameEntered, passwordEntered);
        if (userSystem.containsKey(userEntered)) {
            if (userEntered.getPasswords().equals(passwordEntered)) {
                return true;
            }
            System.out.println("invalid Password");
        }
        System.out.println("invalid Username");
        return false;
    }

    // MODIFIES: User
    // EFFECTS: set user password to passwordentered
    public void resetPasswords(User u, int passwordentered) {
        Password password  = new Password(passwordentered);
        userSystem.remove(u);
        u.setPasswords(password);
        userSystem.put(u, password);
    }

    // MODIFIES: this
    // EFFECTS: add a new user to user system
    public void addUser(User u) {
        userSystem.put(u, u.getPasswords());
    }

    public void addUser(String userName, int password) {
        Password passwordEntered = new Password(password);
        User user = new User(userName, passwordEntered);
        userSystem.put(user, user.getPasswords());
    }

    //EFFECTS: return userSystem;
    public Map<User, Password> getUserSystem() {
        return userSystem;
    }


    // MODIFIES: this
    // EFFECTS: add a default user admin to the user system
    public void defaultUser() {
        User admin = new User("admin");
        addUser(admin);
    }

    public int size() {
        return userSystem.size();
    }

    // EFFECTS: split ArraryList<String>
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(" : ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    @Override
    public void save(String fileName) throws IOException {
        List<String> newLines = new ArrayList<>();
        PrintWriter writer = new PrintWriter(fileName,"UTF-8");
        Set<Map.Entry<User, Password>> entries = userSystem.entrySet();
        for (Map.Entry<User, Password> entry : entries) {
            newLines.add(entry.getKey().getUserName() + " : " + Integer.toString(entry.getValue().getPassword()));
        }
        for (String line : newLines) {
            writer.println(line);
        }
        writer.close();
    }

    @Override
    public void load(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line: lines) {
            ArrayList<String> partsOfLine = splitString(line);
            Password password = new Password(Integer.parseInt(partsOfLine.get(1)));
            User user = new User(partsOfLine.get(0), password);
            userSystem.put(user, password);
        }
    }

    // REQUIRES: input file name to exist
    // MODIFIES: this
    // EFFECTS: empty input file
    @Override
    public void emptyFile(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(fileName,"UTF-8");
        writer.print("");
        writer.close();
    }

}
