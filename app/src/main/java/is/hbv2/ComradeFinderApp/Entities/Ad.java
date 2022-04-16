package is.hbv2.ComradeFinderApp.Entities;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Ad {

    @SerializedName("ID")
    private long ID;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("salaryRange")
    private String salaryRange;
    @SerializedName("extraQuestions")
    private List<String> extraQuestions;
    @SerializedName("companyUsername")
    private String companyUsername;
    @SerializedName("linkToPDFImage")
    private String linkToPDFImage;
    @SerializedName("tags")
    private List<String> tags;
    @SerializedName("applications")
    private List<Application> applications;

    public Ad() {

    }


    public Ad(String title, String description, String salaryRange, List<String> extraQuestions, String companyUsername, String linkToPDFImage, List<String> tags) {
        this.title = title;
        this.description = description;
        this.salaryRange = salaryRange;
        this.extraQuestions = extraQuestions;
        this.companyUsername = companyUsername;
        this.linkToPDFImage = linkToPDFImage;
        this.tags = tags;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public List<String> getExtraQuestions() {
        return extraQuestions;
    }

    public void setExtraQuestions(List<String> extraQuestions) {
        this.extraQuestions = extraQuestions;
    }

    public String getCompanyUsername() {
        return companyUsername;
    }

    public void setCompanyUsername(String companyUsername) {
        this.companyUsername = companyUsername;
    }

    public String getLinkToPDFImage() {
        return linkToPDFImage;
    }

    public void setLinkToPDFImage(String linkToPDFImage) {
        this.linkToPDFImage = linkToPDFImage;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "title='" + title + '\'' +
                ", description=" + description +
                ", priceRange=" + salaryRange +
                ", extraQuestions=" + extraQuestions +
                ", company=" + companyUsername +
                ", linkToPDFImage='" + linkToPDFImage + '\'' +
                ", applications=" + applications +
                ", tags=" + tags +
                '}';
    }
}