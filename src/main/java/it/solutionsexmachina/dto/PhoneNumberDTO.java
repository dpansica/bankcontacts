package it.solutionsexmachina.dto;

public class PhoneNumberDTO {

    private String id;

    private String internationalPrefix;

    private String localPrefix;

    private String number;

    private ContactDTO contact;

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

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }
}
