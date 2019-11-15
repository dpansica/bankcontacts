package it.solutionsexmachina.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ContactDTO {

    private String id;

    private String firstName;

    private String secondName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dob;

    private String picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
