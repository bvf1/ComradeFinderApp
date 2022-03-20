package is.hbv2.ComradeFinderApp.Entities;

import androidx.annotation.NonNull;

import java.util.List;


public class Ad {
    private long ID;
    private String title;
    private String description;
    private String salaryRange;
    private List<String> extraQuestions;
    private String company;
    private String linkToPDFImage;
    private List<String> tags;
    private List<Application> applications;

    public Ad() {

    }


    public Ad(String title, String description, String salaryRange, List<String> extraQuestions, String company, String linkToPDFImage, List<String> tags) {
        this.title = title;
        this.description = description;
        this.salaryRange = salaryRange;
        this.extraQuestions = extraQuestions;
        this.company = company;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
        return "Ad{" +
                "id=" + ID +
                ", title='" + title + '\'' +
                ", description=" + description +
                ", priceRange=" + salaryRange +
                ", extraQuestions=" + extraQuestions +
                ", company=" + company +
                ", linkToPDFImage='" + linkToPDFImage + '\'' +
                ", applications=" + applications +
                ", tags=" + tags +
                '}';
    }
}