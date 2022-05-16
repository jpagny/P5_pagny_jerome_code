package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.FireStationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FireStationModelServiceTest {

    @Autowired
    private FireStationService fireStationService;

    @SpyBean
    DataFromJsonFile data;

    @BeforeEach
    public void setUp() {
        data.getFireStations().clear();
        FireStationModel fireStation = new FireStationModel("29 15th St", "1");
        data.getFireStations().put(fireStation.getId(), fireStation);
    }

    @Test
    @DisplayName("Show all fire stations")
    public void showAllFireStations() {
        Iterable<FireStationModel> fireStations = fireStationService.getFireStations();
        long count = Iterators.size(fireStations.iterator());
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Show information of a fire station by id")
    public void showInformationOfAFireStationById() {
        FireStationModel theFireStation = data.getFireStations().entrySet()
                .stream()
                .findFirst()
                .get().getValue();

        FireStationModel fireStation = fireStationService.getFireStation(theFireStation.getId());
        assertNotNull(fireStation);
        assertEquals(theFireStation, fireStation);
    }

    @Test
    @DisplayName("Show information of a fire station by address ")
    public void showInformationOfAFireStationByAddress() {
        FireStationModel theFireStation = data.getFireStations().entrySet()
                .stream()
                .findFirst()
                .get().getValue();

        Optional<FireStationModel> fireStation = fireStationService.getFireStationByAddress(theFireStation.getAddress());
        assertEquals(theFireStation, fireStation.get());
    }

    @Test
    @DisplayName("Show all fire station by station number")
    public void showAllFireStationByStationNumber() {
        Iterable<FireStationModel> fireStation = fireStationService.getFireStationsByStationNumber(1);
        int countFireStation = Iterators.size(fireStation.iterator());
        assertEquals(1, countFireStation);
    }

    @Test
    @DisplayName("Create a new fire station")
    public void createANewFireStation() {
        FireStationModel fireStationModel = new FireStationModel("29 15th St", "1");
        FireStationModel fireStationModelSaved = fireStationService.saveFireStation(fireStationModel);
        assertEquals(fireStationModel, fireStationModelSaved);
    }

    @Test
    @DisplayName("Update a fire station by id")
    public void updateAFireStationById() {
        FireStationModel fireStation = data.getFireStations().entrySet()
                .stream()
                .findFirst()
                .get().getValue();

        fireStation.setStation("4");

        FireStationModel fireStationModelUpdated = fireStationService.updateFireStation(fireStation);

        assertEquals(fireStation, fireStationModelUpdated);
    }

    @Test
    @DisplayName("Delete a fire station")
    public void deleteAFireStation() {
        fireStationService.deleteFireStation("1");
        assertNull(fireStationService.getFireStation("1"));
    }

}
