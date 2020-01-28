package model;

import java.util.Objects;

public class User {
    private Password passwords;
    private String userName;


    public User(String userName) {
        passwords = new Password(0000);
        this.userName = userName;
    }

    public User(String userName, Password password) {
        this.userName = userName;
        this.passwords = password;
    }

    // EFFECTS: return passwords
    public Password getPasswords() {
        return passwords;
    }

    // EFFECTS: set this.passwords to passwords
    public void setPasswords(Password passwords) {
        if (!(this.passwords == passwords)) {
            this.passwords = passwords;
            passwords.setUser(this);
        }
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(passwords, user.passwords)
                && Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passwords, userName);
    }
}
