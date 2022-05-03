package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Data
@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public Optional<MedicalRecord> getMedicalRecord(final Long id) {
        return medicalRecordRepository.findById(id);
    }

    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public Optional<MedicalRecord> getMedicalRecordByPerson(Person person){
        return StreamSupport.stream(medicalRecordRepository.findAll().spliterator(), false)
                .filter(theMedicalRecord ->
                        person.getFirstName().equalsIgnoreCase(theMedicalRecord.getFirstName())
                                && person.getLastName().equalsIgnoreCase(theMedicalRecord.getLastName()))
                .findFirst();
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord person) {
        MedicalRecord medicalRecordSaved = medicalRecordRepository.save(person);
        return medicalRecordSaved;
    }

    public void deleteMedicalRecord(final Long id) {
        medicalRecordRepository.deleteById(id);
    }


}
