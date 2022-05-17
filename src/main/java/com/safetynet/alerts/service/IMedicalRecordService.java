package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecordModel;
import com.safetynet.alerts.model.PersonModel;

import java.util.Optional;

public interface IMedicalRecordService {
    MedicalRecordModel getMedicalRecord(final String id);

    Iterable<MedicalRecordModel> getMedicalRecords();

    Optional<MedicalRecordModel> getMedicalRecordByPerson(PersonModel personModel);

    MedicalRecordModel saveMedicalRecord(MedicalRecordModel medicalRecordModel);

    MedicalRecordModel updateMedicalRecord(MedicalRecordModel medicalRecordModel);

    void deleteMedicalRecord(final String id);

    int getAge(MedicalRecordModel medicalRecordModel);

    boolean isMinor(MedicalRecordModel medicalRecordModel);
}
