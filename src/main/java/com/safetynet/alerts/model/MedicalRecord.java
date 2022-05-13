package com.safetynet.alerts.model;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class MedicalRecord {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String birthdate;

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;

}
