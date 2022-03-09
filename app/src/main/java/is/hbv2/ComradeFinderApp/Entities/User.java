package is.hbv2.ComradeFinderApp.Entities;

import java.util.List;

public class User extends Account {

    public User() {
    }

    public User(String username, String password, String phone, String email, String displayName, String description) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.displayName = displayName;
        this.description = description;
    }
}