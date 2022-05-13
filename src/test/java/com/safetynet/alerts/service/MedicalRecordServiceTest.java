package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MedicalRecordServiceTest {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @SpyBean
    DataFromJsonFile data;

    @BeforeEach
    public void setUp() {

        data.getMedicalRecord().clear();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId("1");
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Rick");
        medicalRecord.setBirthdate("12/17/2021");
        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(new ArrayList<>());

        data.getMedicalRecord().put("1",medicalRecord);
    }

    @Test
    public void should_find_all_medicalRecords() {
        Iterable fireStations = medicalRecordService.getMedicalRecords();
        long count = Iterators.size(fireStations.iterator());
        assertEquals(1, count);
    }

    @Test
    public void should_find_medicalRecord_by_id() {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord("1");
        assertTrue(medicalRecord != null);
        assertEquals("John", medicalRecord.getFirstName());
    }

    @Test
    public void should_save_medicalRecord() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId("1");
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Rick");
        medicalRecord.setBirthdate("12/17/2021");
        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(new ArrayList<>());

        MedicalRecord medicalRecordSaved = medicalRecordService.saveMedicalRecord(medicalRecord);

        assertEquals(medicalRecord, medicalRecordSaved);
    }

    @Test
    public void should_delete_medicalRecord_by_id() {
        medicalRecordService.deleteMedicalRecord("1");
        assertTrue(medicalRecordService.getMedicalRecord("1") == null);
    }


}
