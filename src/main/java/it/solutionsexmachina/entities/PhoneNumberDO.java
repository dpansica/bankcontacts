package it.solutionsexmachina.entities;

import javax.persistence.*;

@Entity
public class PhoneNumberDO{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;

    private String internationalPrefix;

    private String localPrefix;

    private String number;

    @OneToOne
    private ContactDO contact;

    public String getInternationalPrefix() {
        return internationalPrefix;
    }

    public void setInternationalPrefix(String internationalPrefix) {
        this.internationalPrefix = internationalPrefix;
    }

    public String getLocalPrefix() {
        return localPrefix;
    }

    public void setLocalPrefix(String localPrefix) {
        this.localPrefix = localPrefix;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ContactDO getContact() {
        return contact;
    }

    public void setContact(ContactDO contact) {
        this.contact = contact;
    }
}
