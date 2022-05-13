package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        data.getFireStations().put("1",fireStation);
    }

    @Test
    public void should_find_all_fireStation() {
        Iterable fireStations = fireStationService.getFireStations();
        long count = Iterators.size(fireStations.iterator());
        assertEquals(1, count);
    }

    @Test
    public void should_find_fireStation_by_id() {
        FireStation fireStation = fireStationService.getFireStation("1");
        assertTrue(fireStation != null);
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
    public void should_delete_fireStation_by_id() {
        fireStationService.deleteFireStation("1");
        assertTrue(fireStationService.getFireStation("1") == null);
    }


}
