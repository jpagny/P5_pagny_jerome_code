package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecordModel;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecords")
    public Iterable<MedicalRecordModel> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }

    @GetMapping("/medicalRecord/{id}")
    public MedicalRecordModel getMedicalRecord(@PathVariable final String id) {
        return medicalRecordService.getMedicalRecord(id);
    }

    @PostMapping("/medicalRecord")
    public MedicalRecordModel createMedicalRecord(@RequestBody MedicalRecordModel medicalRecordModelToSave) {
        return medicalRecordService.saveMedicalRecord(medicalRecordModelToSave);
    }

    @PutMapping("/medicalRecord/{id}")
    public MedicalRecordModel updateMedicalRecord(@PathVariable("id") final String id, @RequestBody MedicalRecordModel medicalRecordModelToUpdate) {
        MedicalRecordModel medicalRecordModel = medicalRecordService.getMedicalRecord(id);

        if (medicalRecordModel != null) {

            String firstName = medicalRecordModel.getFirstName();
            String lastName = medicalRecordModel.getLastName();

            String birthdate = medicalRecordModelToUpdate.getBirthdate() == null ? medicalRecordModel.getBirthdate() : medicalRecordModelToUpdate.getBirthdate();
            List<String> medications = medicalRecordModelToUpdate.getMedications() == null ? medicalRecordModel.getMedications() : medicalRecordModelToUpdate.getMedications();
            List<String> allergies = medicalRecordModelToUpdate.getAllergies() == null ? medicalRecordModel.getAllergies() : medicalRecordModelToUpdate.getAllergies();

            MedicalRecordModel medicalRecordModelUpdated = new MedicalRecordModel(id, firstName, lastName, birthdate, medications, allergies);

            medicalRecordService.updateMedicalRecord(medicalRecordModelUpdated);
            return medicalRecordModelUpdated;

        } else {
            return null;

        }
    }

    @DeleteMapping("/medicalRecord/{id}")
    public void deleteMedicalRecord(@PathVariable("id") final String id) {
        medicalRecordService.deleteMedicalRecord(id);
    }

}
