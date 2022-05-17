package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.PersonModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChildAlertDTO {
    private String firstName;
    private String lastName;
    private int age;
    private Iterable<PersonModel> listFamilyMember;
}
