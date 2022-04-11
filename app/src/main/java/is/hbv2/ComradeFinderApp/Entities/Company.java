package is.hbv2.ComradeFinderApp.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Company extends Account {

    @SerializedName("SSN")
    private int SSN;
    @SerializedName("street")
    private String street;
    @SerializedName("companyPhone")
    private String companyPhone;
    @SerializedName("advertisements")
    private List<Ad> advertisements;


    public Company() {
    }

    public Company(String username, String password, String phone,
                   String email, String displayName, String description,
                   int SSN, String street, String companyPhone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.displayName = displayName;
        this.description = description;
        this.SSN = SSN;
        this.street = street;
        this.companyPhone = companyPhone;
    }

    // GETTERS AND SETTERS

    public int getSSN() {
        return SSN;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public List<Ad> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Ad> advertisements) {
        this.advertisements = advertisements;
    }

    public String getType() {
        return "company";
    }


}