package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.MedicalRecordModel;
import com.safetynet.alerts.model.PersonModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    private PersonModel person;

    @BeforeEach
    public void setUp() {
        data.getMedicalRecords().clear();

        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");

        MedicalRecordModel medicalRecord = new MedicalRecordModel("1", "John", "Rick", "12/17/1985", medications, new ArrayList<>());

        data.getMedicalRecords().put(medicalRecord.getId(), medicalRecord);

        person = new PersonModel("John", "Rick", "29 15th St", "Chicago", "365781", "john.rick@gmail.com", "07894235694");
    }

    @Test
    @DisplayName("Show all medical records")
    public void should_find_all_medicalRecords() {
        Iterable<MedicalRecordModel> medicalRecords = medicalRecordService.getMedicalRecords();
        long count = Iterators.size(medicalRecords.iterator());
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Show medical record by id")
    public void should_find_medicalRecord_by_id() {
        MedicalRecordModel medicalRecordModel = data.getMedicalRecords().entrySet()
                .stream()
                .findFirst()
                .get().getValue();

        MedicalRecordModel medicalRecordToFind = medicalRecordService.getMedicalRecord(medicalRecordModel.getId());
        assertNotNull(medicalRecordToFind);
        assertEquals(medicalRecordModel, medicalRecordToFind);
    }

    @Test
    @DisplayName("Show medical record by person")
    public void showMedicalRecordByPerson() {
        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        MedicalRecordModel theMedicalRecord = new MedicalRecordModel("1", "John", "Rick", "12/17/1985", medications, new ArrayList<>());

        Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(person);

        assertEquals(theMedicalRecord.getId(), medicalRecord.get().getId());
    }

    @Test
    @DisplayName("Return empty when medical record has a unknown first name")
    public void returnEmptyWhenMedicalRecordHasAUnknownFirstName() {
        person.setFirstName("Johna");
        Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(person);
        assertTrue(medicalRecord.isEmpty());
    }

    @Test
    @DisplayName("Return empty when medical record has a unknown last name")
    public void returnEmptyWhenMedicalRecordHasAUnknownLastName() {
        person.setLastName("Ricka");
        Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(person);
        assertTrue(medicalRecord.isEmpty());
    }

    @Test
    @DisplayName("Create a new medical record")
    public void createANewMedicalRecord() {
        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");

        MedicalRecordModel medicalRecord = new MedicalRecordModel("John","Rick","12/17/2021",medications,new ArrayList<>());

        MedicalRecordModel medicalRecordSaved = medicalRecordService.saveMedicalRecord(medicalRecord);

        assertEquals(medicalRecord, medicalRecordSaved);
    }


    @Test
    @DisplayName("Update a medical record by id")
    public void updateAMedicalRecordById() {
        MedicalRecordModel medicalRecord = data.getMedicalRecords().entrySet()
                .stream()
                .findFirst()
                .get().getValue();

        medicalRecord.setBirthdate("12/12/1956");

        MedicalRecordModel medicalRecordUpdated = medicalRecordService.updateMedicalRecord(medicalRecord);

        assertEquals(medicalRecord, medicalRecordUpdated);
    }


    @Test
    @DisplayName("Delete a medical record by id")
    public void deleteAMedicalRecordById() {
        medicalRecordService.deleteMedicalRecord("1");
        assertNull(medicalRecordService.getMedicalRecord("1"));
    }
    
    @Test
    @DisplayName("Show the age of a person")
    public void showTheAgeOfAPerson() {
        int age = medicalRecordService.getAge(data.getMedicalRecords().get("1"));
        assertEquals(36, age);
    }

    @Test
    @DisplayName("Person is not minor when person has more than 18 old")
    public void personIsNotMinorWhenPersonHasMoreThan18Old() {
        MedicalRecordModel medicalRecord = data.getMedicalRecords().get("1");
        assertFalse(medicalRecordService.isMinor(medicalRecord));
    }

    @Test
    @DisplayName("Person is minor when person has less than 18 old")
    public void personIsMinorWhenPersonHasLessThan18Old () {
        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        MedicalRecordModel medicalRecord = new MedicalRecordModel("John","Ricka","12/17/2020",medications,new ArrayList<>());

        data.getMedicalRecords().put(medicalRecord.getId(), medicalRecord);

        MedicalRecordModel theMinor = data.getMedicalRecords().get(medicalRecord.getId());
        assertTrue(medicalRecordService.isMinor(theMinor));
    }

}
