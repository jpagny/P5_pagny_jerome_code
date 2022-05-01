package com.safetynet.alerts.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String address;

    private String city;

    private String zip;

    private String phone;

    private String email;

}
