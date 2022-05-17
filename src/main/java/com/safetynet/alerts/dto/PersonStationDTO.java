package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PersonStationDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
}
