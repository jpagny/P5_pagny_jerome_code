package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class HouseholdFloodDTO {
    String address;
    String stationNumber;
    List<PersonFloodDTO> listPersonFloodDTO;
}
