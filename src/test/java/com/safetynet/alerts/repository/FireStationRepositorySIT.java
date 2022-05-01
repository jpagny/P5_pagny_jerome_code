package com.safetynet.alerts.repository;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.config.IntegrationTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(IntegrationTestConfig.class)
public class FireStationRepositorySIT {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Test
    public void should_find_all_fireStations() {
        Iterable fireStations = fireStationRepository.findAll();
        long count = Iterators.size(fireStations.iterator());
        assertEquals(3, count);
    }

    @Test
    public void should_find_fireStation_by_id() {
        Optional<FireStation> fireStation = fireStationRepository.findById(Long.parseLong("1"));
        assertEquals("3", fireStation.get().getStation());
    }

    @Test
    public void should_delete_fireStation_by_id() {
        fireStationRepository.deleteById(Long.parseLong("1"));
        Optional<FireStation> fireStation = fireStationRepository.findById(Long.parseLong("1"));
        assertThat(fireStation).isEmpty();
    }


}
