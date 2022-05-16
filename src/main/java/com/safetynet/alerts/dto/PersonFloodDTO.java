package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PersonFloodDTO {
    private String lastName;
    private final List<String> medications;
    private final List<String> allergies;
    private String phone;
    private int age;
}
