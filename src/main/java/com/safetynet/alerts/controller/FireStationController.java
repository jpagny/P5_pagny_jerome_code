package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/firestations")
    public Iterable<FireStation> getFireStations() {
        return fireStationService.getFireStations();
    }

    @GetMapping("/firestation/{id}")
    public Optional<FireStation> getFireStation(@PathVariable final Long id) {
        return fireStationService.getFireStation(id);
    }

    @PostMapping("/firestation")
    public FireStation createPerson(@RequestBody FireStation fireStationToSave) {
        return fireStationService.saveFireStation(fireStationToSave);
    }

    @PutMapping("/firestation/{id}")
    public FireStation updateFireStation(@PathVariable("id") final Long id, @RequestBody FireStation fireStationToUpdate) {
        Optional<FireStation> fireStation = fireStationService.getFireStation(id);

        if (fireStation.isPresent()) {

            FireStation currentFireStation = fireStation.get();
            String address = fireStationToUpdate.getAddress();
            String station = fireStationToUpdate.getStation();

            if (address != null) {
                currentFireStation.setAddress(address);
            }

            if (station != null) {
                currentFireStation.setStation(station);
            }

            fireStationService.saveFireStation(currentFireStation);
            return currentFireStation;

        } else {
            return null;

        }
    }

    @DeleteMapping("/firestation/{id}")
    public void deleteFireStation(@PathVariable("id") final Long id) {
        fireStationService.deleteFireStation(id);
    }

}
