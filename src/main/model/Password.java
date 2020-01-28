package model;

import java.util.Objects;

public class Password {

    private User user;
    private int password;

    public Password(int password) {
        this.password = password;
    }

    public void setUser(User u) {
        if (!(this.user == u)) {
            this.user = u;
            u.setPasswords(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return this.password == password.password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    public User getUser() {
        return user;
    }

    public int getPassword() {
        return password;
    }

}
