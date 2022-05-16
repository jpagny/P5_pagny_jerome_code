package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FireStationServiceTest {

    @Autowired
    private FireStationService fireStationService;

    @SpyBean
    DataFromJsonFile data;

    @BeforeEach
    public void setUp() {

        data.getFireStations().clear();

        FireStation fireStation = new FireStation();
        fireStation.setId("1");
        fireStation.setAddress("29 15th St");
        fireStation.setStation("1");

        data.getFireStations().put("1", fireStation);
    }

    @Test
    public void should_find_all_fireStation() {
        Iterable<FireStation> fireStations = fireStationService.getFireStations();
        long count = Iterators.size(fireStations.iterator());
        assertEquals(1, count);
    }

    @Test
    public void should_find_fireStation_by_id() {
        FireStation fireStation = fireStationService.getFireStation("1");
        assertNotNull(fireStation);
        assertEquals("1", fireStation.getStation());
    }

    @Test
    public void should_save_fireStation() {
        FireStation fireStation = new FireStation();
        fireStation.setId("1");
        fireStation.setAddress("29 15th St");
        fireStation.setStation("1");

        FireStation fireStationSaved = fireStationService.saveFireStation(fireStation);

        assertEquals(fireStation, fireStationSaved);
    }

    @Test
    public void should_update_fireStation(){
        FireStation fireStation = data.getFireStations().get("1");
        fireStation.setAddress("xxxx");

        FireStation fireStationUpdated = fireStationService.updateFireStation(fireStation);

        assertEquals(fireStation, fireStationUpdated);
    }

    @Test
    public void should_delete_fireStation_by_id() {
        fireStationService.deleteFireStation("1");
        assertNull(fireStationService.getFireStation("1"));
    }

    @Test
    public void should_return_AFireStation_byAnAddress(){
        Optional<FireStation> fireStation = fireStationService.getFireStationByAddress("29 15th St");
        assertEquals(data.getFireStations().get("1"),fireStation.get());
    }

    @Test
    public void should_return_AFireStation_byAStationNumber(){
        Iterable<FireStation> fireStation = fireStationService.getFireStationsByStationNumber(1);
        int countFireStation = Iterators.size(fireStation.iterator());

        assertEquals(1,countFireStation);
        assertEquals(data.getFireStations().get("1"),fireStation.iterator().next());
    }

}
