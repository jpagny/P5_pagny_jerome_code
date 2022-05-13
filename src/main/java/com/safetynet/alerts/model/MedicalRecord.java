package com.safetynet.alerts.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class MedicalRecord {

    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String birthdate;

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;

}
