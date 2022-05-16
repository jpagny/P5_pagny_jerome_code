package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PersonModel {

    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;


    public PersonModel() {}

    public PersonModel(final String theFirstName, final String theLastName,
                       final String theAddress, final String theCity,
                       final String theZip, final String thePhone,
                       final String theEmail) {
        this.id = UUID.randomUUID().toString();
        this.firstName = theFirstName;
        this.lastName = theLastName;
        this.address = theAddress;
        this.city = theCity;
        this.zip = theZip;
        this.phone = thePhone;
        this.email = theEmail;
    }

}
