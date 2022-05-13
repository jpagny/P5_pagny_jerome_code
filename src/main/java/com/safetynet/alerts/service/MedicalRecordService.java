package com.safetynet.alerts.service;

import com.safetynet.alerts.constant.App;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
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
        return data.getMedicalRecord().get(id);
    }

    public Iterable<MedicalRecord> getMedicalRecords() {
        return data.getMedicalRecord().values();
    }

    public Optional<MedicalRecord> getMedicalRecordByPerson(Person person) {
        return data.getMedicalRecord().values().stream()
                .filter(theMedicalRecord ->
                        person.getFirstName().equalsIgnoreCase(theMedicalRecord.getFirstName())
                                && person.getLastName().equalsIgnoreCase(theMedicalRecord.getLastName()))
                .findFirst();
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        String uniqueID = UUID.randomUUID().toString();
        data.getMedicalRecord().put(uniqueID, medicalRecord);
        return data.getMedicalRecord().get(uniqueID);
    }

    public void deleteMedicalRecord(final String id) {
        data.getMedicalRecord().remove(id);
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
