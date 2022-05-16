package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class DataFromJsonFile {
    private Map<String, PersonModel> persons;
    private Map<String, FireStation> fireStations;
    private Map<String, MedicalRecordModel> medicalRecords;
}
