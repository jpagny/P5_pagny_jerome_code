package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MedicalRecordServiceTest {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @MockBean
    private MedicalRecordRepository medicalRecordRepository;

    @BeforeEach
    public void setUp() {
        ArrayList<MedicalRecord> listMedicalRecord = new ArrayList<>();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(Long.parseLong("1"));
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Rick");
        medicalRecord.setBirthdate("12/17/2021");
        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(new ArrayList<>());

        listMedicalRecord.add(medicalRecord);

        when(medicalRecordRepository.findById(Long.parseLong("1"))).thenReturn(Optional.of(medicalRecord));
        when(medicalRecordRepository.findAll()).thenReturn(listMedicalRecord);
        doNothing().when(medicalRecordRepository).deleteById(Long.parseLong("1"));
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);
    }

    @Test
    public void should_find_all_medicalRecords() {
        Iterable fireStations = medicalRecordService.getMedicalRecords();
        long count = Iterators.size(fireStations.iterator());
        assertEquals(1, count);
    }

    @Test
    public void should_find_medicalRecord_by_id() {
        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecord(Long.parseLong("1"));
        assertTrue(medicalRecord.isPresent());
        assertEquals("John", medicalRecord.get().getFirstName());
    }

    @Test
    public void should_save_medicalRecord() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(Long.parseLong("1"));
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
        medicalRecordService.deleteMedicalRecord(Long.parseLong("1"));
        verify(medicalRecordRepository, times(1)).deleteById(any(Long.class));
    }


}
