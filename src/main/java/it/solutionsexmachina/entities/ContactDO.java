package it.solutionsexmachina.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class ContactDO{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;

    private String firstName;

    private String secondName;

    private Date dob;

    @Lob
    private String picture;

    @OneToMany(mappedBy = "contact")
    private List<AddressDO> addresses;

    @OneToMany(mappedBy = "contact")
    private List<PhoneNumberDO> phoneNumbers;

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

    public List<AddressDO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDO> addresses) {
        this.addresses = addresses;
    }

    public List<PhoneNumberDO> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumberDO> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
