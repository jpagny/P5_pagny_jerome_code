package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    public Optional<FireStation> getFireStation(final Long id) {
        return fireStationRepository.findById(id);
    }

    public Iterable<FireStation> getFireStations() {
        return fireStationRepository.findAll();
    }

    public FireStation saveFireStation(FireStation fireStation) {
        return fireStationRepository.save(fireStation);
    }

    public void deleteFireStation(final Long id) {
        fireStationRepository.deleteById(id);
    }
}
