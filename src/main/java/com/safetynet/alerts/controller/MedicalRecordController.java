package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecords")
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }

    @GetMapping("/medicalRecord/{id}")
    public Optional<MedicalRecord> getMedicalRecord(@PathVariable final Long id) {
        return medicalRecordService.getMedicalRecord(id);
    }

    @PostMapping("/medicalRecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecordToSave) {
        return medicalRecordService.saveMedicalRecord(medicalRecordToSave);
    }

    @PutMapping("/medicalRecord/{id}")
    public MedicalRecord updateMedicalRecord(@PathVariable("id") final Long id, @RequestBody MedicalRecord medicalRecordToUpdate) {
        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecord(id);

        if (medicalRecord.isPresent()) {

            MedicalRecord currentMedicalRecord = medicalRecord.get();
            String birthdate = medicalRecordToUpdate.getBirthdate();
            List<String> medications = medicalRecordToUpdate.getMedications();
            List<String> allergies = medicalRecordToUpdate.getAllergies();

            if (birthdate != null) {
                currentMedicalRecord.setBirthdate(birthdate);
            }

            if (medications != null) {
                currentMedicalRecord.setMedications(medications);
            }

            if (allergies != null) {
                currentMedicalRecord.setAllergies(allergies);
            }

            medicalRecordService.saveMedicalRecord(currentMedicalRecord);
            return currentMedicalRecord;

        } else {
            return null;

        }
    }

    @DeleteMapping("/medicalRecord/{id}")
    public void deletePerson(@PathVariable("id") final Long id) {
        medicalRecordService.deleteMedicalRecord(id);
    }

}
