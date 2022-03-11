package is.hbv2.ComradeFinderApp.Entities;

import java.util.List;

public class Ad {
    private long ID;
    private String title;
    private List<String> description;
    private String priceRange;
    private List<String> extraQuestions;
    private Company company;
    private String linkToPDFImage;
    private List<Application> applications;
    private List<String> tags;

    // Constructor chain
    public Ad() {}


    public Ad(String title, List<String> description, List<String> extraQuestions, Company company, String linkToPDFImage) {
        this.title = title;
        this.description = description;
        this.priceRange = priceRange;
        this.extraQuestions = extraQuestions;
        this.company = company;
        this.linkToPDFImage = linkToPDFImage;
    }

    // GETTER ANS SETTERS

    public void addTag(String tag) {
        this.tags.add(tag);
    }
    public void addTags(List<String> tags) {
        this.tags.addAll(tags);
    }

    public void removeTag(String tag) {
        this.tags.remove(tag);
    }
    public void removeTags(List<String> tags) {
        this.tags.removeAll(tags);
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

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getPriceRange() { return priceRange; }

    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }

    public List<String> getExtraQuestions() {
        return extraQuestions;
    }

    public void setExtraQuestions(List<String> extraQuestions) {
        this.extraQuestions = extraQuestions;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getLinkToPDFImage() {
        return linkToPDFImage;
    }

    public void setLinkToPDFImage(String linkToPDFImage) {
        this.linkToPDFImage = linkToPDFImage;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + ID +
                ", title='" + title + '\'' +
                ", description=" + description +
                ", priceRange=" + priceRange +
                ", extraQuestions=" + extraQuestions +
                ", company=" + company +
                ", linkToPDFImage='" + linkToPDFImage + '\'' +
                ", applications=" + applications +
                ", tags=" + tags +
                '}';
    }
}