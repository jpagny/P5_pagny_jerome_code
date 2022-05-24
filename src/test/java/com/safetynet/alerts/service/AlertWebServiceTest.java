package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.FireStationModel;
import com.safetynet.alerts.model.MedicalRecordModel;
import com.safetynet.alerts.model.PersonModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlertWebServiceTest {

    @Autowired
    private AlertWebService alertWebService;

    @SpyBean
    DataFromJsonFile data;


    @BeforeEach
    public void setUp() {

        data.getPersons().clear();
        data.getMedicalRecords().clear();
        data.getFireStations().clear();

        Map<String, PersonModel> listPerson = new HashMap<>();
        listPerson.put("JohBoy", new PersonModel("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
        listPerson.put("TenBoy", new PersonModel("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"));
        listPerson.put("TesCar", new PersonModel("Tessa", "Carman", "834 Binoc Ave", "Binoc", "97451", "841-874-6512", "tenz@email.com"));

        data.getPersons().putAll(listPerson);

        Map<String, MedicalRecordModel> listMedicalRecords = new HashMap<>();
        ArrayList<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        medications.add("noznazol:250mg");
        ArrayList<String> allergies = new ArrayList<>();
        allergies.add("peanut");

        listMedicalRecords.put("JohBoy", new MedicalRecordModel("John", "Boyd", "03/06/1984", medications, allergies));
        listMedicalRecords.put("TenBoy", new MedicalRecordModel("Tenley", "Boyd", "02/18/2020", medications, allergies));

        data.getMedicalRecords().putAll(listMedicalRecords);

        Map<String, FireStationModel> listFireStations = new HashMap<>();
        listFireStations.put("1", new FireStationModel("1509 Culver St", "3"));
        listFireStations.put("2", new FireStationModel("834 Binoc Ave", "2"));

        data.getFireStations().putAll(listFireStations);

    }

    @Test
    public void getListEmailByCity() {
        List<String> listEmail = alertWebService.getListEmailByCity("Culver");
        assertEquals(2, Iterators.size(listEmail.iterator()));
    }

    @Test
    public void getPersonsAndNumberFireStationByAddress() {
        FireDTO listPersons = alertWebService.getPersonsAndNumberFireStationByAddress("1509 Culver St");
        assertEquals("1509 Culver St", listPersons.getAddress());
        assertEquals(2, listPersons.getListOfHousehold().size());
    }

    @Test
    public void getInformationPerson() {
        List<PersonInfoDTO> listPersons = alertWebService.getInformationPerson("John", "Boyd");
        assertEquals(2, listPersons.size());
        assertEquals("1509 Culver St", listPersons.get(0).getAddress());
        assertEquals("Boyd", listPersons.get(0).getLastName());
    }

    @Test
    public void getListOfChildrenByAddress() {
        List<ChildAlertDTO> listChildren = alertWebService.getListOfChildrenByAddress("1509 Culver St");
        assertEquals(1, listChildren.size());
        assertEquals(2, listChildren.get(0).getAge());
    }

    @Test
    public void getListOfPhoneNumberByFireStationNumber() {
        List<String> listPhone = alertWebService.getListOfPhoneNumberByFireStationNumber("1");
        assertEquals(1, listPhone.size());
        assertEquals("841-874-6512", listPhone.get(0));
    }

    @Test
    public void getListOfHouseholdByStationNumber() {
        List<HouseholdFireStationDTO> listHouseHoldByStations = alertWebService.getListOfHouseholdByStationNumber("2");
        assertEquals(1, listHouseHoldByStations.size());
        assertEquals("Tessa", listHouseHoldByStations.get(0).getListOfPersonStationDTO().get(0).getFirstName());
        assertEquals("Carman", listHouseHoldByStations.get(0).getListOfPersonStationDTO().get(0).getLastName());
    }

    @Test
    public void getListOfPersonByStationsNumber() {
        int[] listStation = {3};
        List<HouseholdFloodDTO> listHouseholdFloodDTO = alertWebService.getListOfPersonByStationsNumber(listStation);
        assertEquals("1509 Culver St", listHouseholdFloodDTO.get(0).getAddress());
        assertEquals(2, listHouseholdFloodDTO.get(0).getListPersonFloodDTO().size());
    }


}
