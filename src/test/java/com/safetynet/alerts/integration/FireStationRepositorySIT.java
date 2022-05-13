package com.safetynet.alerts.integration;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.integration.config.IntegrationTestConfig;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {IntegrationTestConfig.class})
public class FireStationRepositorySIT {

    @Autowired
    private FireStationService fireStationService;

    @Test
    public void should_find_all_fireStations() {
        Iterable<FireStation> fireStations = fireStationService.getFireStations();
        long count = Iterators.size(fireStations.iterator());
        assertEquals(3, count);
    }

    @Test
    public void should_find_fireStation_by_id() {
        FireStation fireStation = fireStationService.getFireStations().iterator().next();
        String id = fireStation.getId();
        fireStation = fireStationService.getFireStation(id);
        assertTrue(fireStation != null);
    }

    @Test
    public void should_delete_fireStation_by_id() {
        fireStationService.deleteFireStation("1");
        FireStation fireStation = fireStationService.getFireStation("1");
        assertThat(fireStation).isNull();
    }


}
