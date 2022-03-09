package is.hbv2.ComradeFinderApp.Entities;


public class Application {
    private long ID;
    private User user;
    private Ad ad;

    public Application() {}
    public Application(User user, Ad ad) {
        this.user = user;
        this.ad = ad;
    }


    public Long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}