package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class HouseholdFireStationDTO {
    private String numberStation;
    private List<PersonStationDTO> listOfPersonStationDTO;
    private int countAdultPersons;
    private int countChildPersons;
}
