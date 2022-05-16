package com.safetynet.alerts.service;

import com.safetynet.alerts.constant.App;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.MedicalRecordModel;
import com.safetynet.alerts.model.PersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class MedicalRecordService {

    @Autowired
    private DataFromJsonFile data;

    public MedicalRecordModel getMedicalRecord(final String id) {
        return data.getMedicalRecords().get(id);
    }

    public Iterable<MedicalRecordModel> getMedicalRecords() {
        return data.getMedicalRecords().values();
    }

    public Optional<MedicalRecordModel> getMedicalRecordByPerson(PersonModel personModel) {
        return data.getMedicalRecords().values().stream()
                .filter(theMedicalRecord ->
                        personModel.getFirstName().equalsIgnoreCase(theMedicalRecord.getFirstName())
                                && personModel.getLastName().equalsIgnoreCase(theMedicalRecord.getLastName()))
                .findFirst();
    }

    public MedicalRecordModel saveMedicalRecord(MedicalRecordModel medicalRecordModel) {
        data.getMedicalRecords().put(medicalRecordModel.getId(), medicalRecordModel);
        return data.getMedicalRecords().get(medicalRecordModel.getId());
    }

    public MedicalRecordModel updateMedicalRecord(MedicalRecordModel medicalRecordModel){
        data.getMedicalRecords().put(medicalRecordModel.getId(), medicalRecordModel);
        return medicalRecordModel;
    }

    public void deleteMedicalRecord(final String id) {
        data.getMedicalRecords().remove(id);
    }

    public int getAge(MedicalRecordModel medicalRecordModel) {
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.parse(medicalRecordModel.getBirthdate(), DateTimeFormatter.ofPattern(App.DATE_FORMAT));
        return Period.between(birthday, today).getYears();
    }

    public boolean isMinor(MedicalRecordModel medicalRecordModel) {
        return getAge(medicalRecordModel) < App.AGE_OF_MAJORITY;
    }


}
