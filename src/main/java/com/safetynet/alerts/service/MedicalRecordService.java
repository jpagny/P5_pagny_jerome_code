package com.safetynet.alerts.service;

import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Data
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
        return StreamSupport.stream(data.getMedicalRecord().values().spliterator(), false)
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


}
