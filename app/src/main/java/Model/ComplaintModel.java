package Model;

public class ComplaintModel {

    String complaintDescription;
    String complaintTitle;
    String complaintType;
    String date;
    String documentid;
    String email;
    String feedbackDate;
    String feedbackDeitail;
    String feedbackTitle;
    String status;
    String userid;

    public ComplaintModel() {
    }

    public ComplaintModel(String complaintDescription, String complaintTitle, String complaintType, String date, String documentid, String email, String feedbackDate, String feedbackDeitail, String feedbackTitle, String status, String userid) {
        this.complaintDescription = complaintDescription;
        this.complaintTitle = complaintTitle;
        this.complaintType = complaintType;
        this.date = date;
        this.documentid = documentid;
        this.email = email;
        this.feedbackDate = feedbackDate;
        this.feedbackDeitail = feedbackDeitail;
        this.feedbackTitle = feedbackTitle;
        this.status = status;
        this.userid = userid;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        this.complaintTitle = complaintTitle;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocumentid() {
        return documentid;
    }

    public void setDocumentid(String documentid) {
        this.documentid = documentid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getFeedbackDeitail() {
        return feedbackDeitail;
    }

    public void setFeedbackDeitail(String feedbackDeitail) {
        this.feedbackDeitail = feedbackDeitail;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}