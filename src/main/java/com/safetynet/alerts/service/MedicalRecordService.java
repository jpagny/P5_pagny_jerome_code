package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public MedicalRecord saveMedicalRecord(MedicalRecord person) {
        MedicalRecord medicalRecordSaved = medicalRecordRepository.save(person);
        return medicalRecordSaved;
    }

    public void deleteMedicalRecord(final Long id) {
        medicalRecordRepository.deleteById(id);
    }


}
