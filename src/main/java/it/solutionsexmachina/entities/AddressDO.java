package it.solutionsexmachina.entities;

import javax.persistence.*;

@Entity
public class AddressDO{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;

    private String street;

    private String postalCode;

    private String town;

    private String country;

    @OneToOne
    private ContactDO contact;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
