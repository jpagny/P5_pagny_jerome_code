package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FireStationServiceTest {

    @Autowired
    private FireStationService fireStationService;

    @MockBean
    private FireStationRepository fireStationRepository;

    @BeforeEach
    public void setUp() {
        ArrayList<FireStation> listFireStation = new ArrayList<>();

        FireStation fireStation = new FireStation();
        fireStation.setId(Long.parseLong("1"));
        fireStation.setAddress("29 15th St");
        fireStation.setStation("1");

        listFireStation.add(fireStation);

        when(fireStationRepository.findById(Long.parseLong("1"))).thenReturn(Optional.of(fireStation));
        when(fireStationRepository.findAll()).thenReturn(listFireStation);
        doNothing().when(fireStationRepository).deleteById(Long.parseLong("1"));
        when(fireStationRepository.save(fireStation)).thenReturn(fireStation);
    }

    @Test
    public void should_find_all_fireStation() {
        Iterable fireStations = fireStationService.getFireStations();
        long count = Iterators.size(fireStations.iterator());
        assertEquals(1, count);
    }

    @Test
    public void should_find_fireStation_by_id() {
        Optional<FireStation> fireStation = fireStationService.getFireStation(Long.parseLong("1"));
        assertTrue(fireStation.isPresent());
        assertEquals("1", fireStation.get().getStation());
    }

    @Test
    public void should_save_fireStation() {
        FireStation fireStation = new FireStation();
        fireStation.setId(Long.parseLong("1"));
        fireStation.setAddress("29 15th St");
        fireStation.setStation("1");

        FireStation fireStationSaved = fireStationService.saveFireStation(fireStation);

        assertEquals(fireStation, fireStationSaved);
    }

    @Test
    public void should_delete_person_by_id() {
        fireStationService.deleteFireStation(Long.parseLong("1"));
        verify(fireStationRepository, times(1)).deleteById(any(Long.class));
    }


}
