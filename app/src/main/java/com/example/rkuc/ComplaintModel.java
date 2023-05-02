package com.example.rkuc;

public class ComplaintModel {

    String complaintDescription;
    String complaintTitle;
    String complaintType;
    String date;
    String documentid;
    String email;
    String status;
    String userid;

    public ComplaintModel() {
    }

    public ComplaintModel(String complaintDescription, String complaintTitle, String complaintType, String date, String documentid, String email, String status, String userid) {
        this.complaintDescription = complaintDescription;
        this.complaintTitle = complaintTitle;
        this.complaintType = complaintType;
        this.date = date;
        this.documentid = documentid;
        this.email = email;
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