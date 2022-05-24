package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MedicalRecordModel {

    private String id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    public MedicalRecordModel() {
    }

    public MedicalRecordModel(String theFirstName, String theLastName, String theBirthdate, List<String> theMedications, List<String> theAllergies) {
        id = theFirstName.substring(0,3) + theLastName.substring(0,3);
        firstName = theFirstName;
        lastName = theLastName;
        birthdate = theBirthdate;
        medications = theMedications;
        allergies = theAllergies;
    }

}
