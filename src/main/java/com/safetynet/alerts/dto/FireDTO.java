package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FireDTO {
    private String address;
    private String numberStation;
    private List<HouseholdDTO> listOfHousehold;
}

