package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalRecordServiceTest {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @SpyBean
    DataFromJsonFile data;

    @BeforeEach
    public void setUp() {

        data.getMedicalRecords().clear();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId("1");
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Rick");
        medicalRecord.setBirthdate("12/17/1985");
        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(new ArrayList<>());

        data.getMedicalRecords().put("1", medicalRecord);
    }

    @Test
    public void should_find_all_medicalRecords() {
        Iterable<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        long count = Iterators.size(medicalRecords.iterator());
        assertEquals(1, count);
    }

    @Test
    public void should_find_medicalRecord_by_id() {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord("1");
        assertNotNull(medicalRecord);
        assertEquals("John", medicalRecord.getFirstName());
    }

    @Test
    public void should_find_medicalRecord_by_person(){
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Rick");

        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(person);

        assertEquals(medicalRecordService.getMedicalRecord("1"),medicalRecord.get());
    }

    @Test
    public void should_be_empty_when_unfounded_medicalRecord_by_person_with_FirstName(){
        Person person = new Person();
        person.setFirstName("Johno");
        person.setLastName("Rick");

        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(person);

        assertTrue(medicalRecord.isEmpty());
    }

    @Test
    public void should_be_empty_when_unfounded_medicalRecord_by_person_with_LastName(){
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Ricka");

        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(person);

        assertTrue(medicalRecord.isEmpty());
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
    public void should_update_medicalRecord(){
        MedicalRecord medicalRecord = data.getMedicalRecords().get("1");
        medicalRecord.setBirthdate("xxx");

        MedicalRecord medicalRecordUpdated = medicalRecordService.updateMedicalRecord(medicalRecord);

        assertEquals(medicalRecord, medicalRecordUpdated);
    }

    @Test
    public void should_delete_medicalRecord_by_id() {
        medicalRecordService.deleteMedicalRecord("1");
        assertNull(medicalRecordService.getMedicalRecord("1"));
    }

    @Test
    public void should_return_age(){
        int age = medicalRecordService.getAge(data.getMedicalRecords().get("1"));
        assertEquals(36,age);
    }

    @Test
    public void should_beNotMinor_When_ageIsMoreThan18(){
        MedicalRecord medicalRecord = data.getMedicalRecords().get("1");
        assertFalse(medicalRecordService.isMinor(medicalRecord));
    }

    @Test
    public void should_beMinor_When_ageIsLessThan18(){

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId("2");
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Ricka");
        medicalRecord.setBirthdate("12/17/2020");
        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(new ArrayList<>());

        data.getMedicalRecords().put("2", medicalRecord);

        MedicalRecord theMinor = data.getMedicalRecords().get("2");
        assertTrue(medicalRecordService.isMinor(theMinor));
    }


}
