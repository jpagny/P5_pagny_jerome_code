package com.safetynet.alerts.service;

import com.safetynet.alerts.constant.App;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.PersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicalRecordService {

    @Autowired
    private DataFromJsonFile data;

    public MedicalRecord getMedicalRecord(final String id) {
        return data.getMedicalRecords().get(id);
    }

    public Iterable<MedicalRecord> getMedicalRecords() {
        return data.getMedicalRecords().values();
    }

    public Optional<MedicalRecord> getMedicalRecordByPerson(PersonModel personModel) {
        return data.getMedicalRecords().values().stream()
                .filter(theMedicalRecord ->
                        personModel.getFirstName().equalsIgnoreCase(theMedicalRecord.getFirstName())
                                && personModel.getLastName().equalsIgnoreCase(theMedicalRecord.getLastName()))
                .findFirst();
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        String uniqueID = UUID.randomUUID().toString();
        medicalRecord.setId(uniqueID);
        data.getMedicalRecords().put(uniqueID, medicalRecord);
        return data.getMedicalRecords().get(uniqueID);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord){
        data.getMedicalRecords().put(medicalRecord.getId(),medicalRecord);
        return medicalRecord;
    }

    public void deleteMedicalRecord(final String id) {
        data.getMedicalRecords().remove(id);
    }

    public int getAge(MedicalRecord medicalRecord) {
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.parse(medicalRecord.getBirthdate(), DateTimeFormatter.ofPattern(App.DATE_FORMAT));
        return Period.between(birthday, today).getYears();
    }

    public boolean isMinor(MedicalRecord medicalRecord) {
        return getAge(medicalRecord) < App.AGE_OF_MAJORITY;
    }


}
