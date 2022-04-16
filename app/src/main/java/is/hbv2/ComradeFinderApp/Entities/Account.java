package is.hbv2.ComradeFinderApp.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class Account {
    @SerializedName("id")
    protected long ID;
    @SerializedName("username")
    protected String username;
    @SerializedName("password")
    protected String password;
    @SerializedName("phone")
    protected String phone;
    @SerializedName("email")
    protected String email;
    @SerializedName("displayName")
    protected String displayName;
    @SerializedName("description")
    protected String description;

    // Abstract class does not initalize
    // No constructor needed.

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}