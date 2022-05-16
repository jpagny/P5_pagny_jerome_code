package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.PersonModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ChildAlertDTO {
    private String firstName;
    private String lastName;
    private int age;
    private Iterable<PersonModel> listFamilyMember;
}
