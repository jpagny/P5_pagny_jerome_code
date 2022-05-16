package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/firestations")
    public Iterable<FireStation> getFireStations() {
        return fireStationService.getFireStations();
    }

    @GetMapping("/firestation/{id}")
    public FireStation getFireStation(@PathVariable final String id) {
        return fireStationService.getFireStation(id);
    }

    @PostMapping("/firestation")
    public FireStation createPerson(@RequestBody FireStation fireStationToSave) {
        return fireStationService.saveFireStation(fireStationToSave);
    }

    @PutMapping("/firestation/{id}")
    public FireStation updateFireStation(@PathVariable("id") final String id, @RequestBody FireStation fireStationToUpdate) {
        FireStation fireStation = fireStationService.getFireStation(id);

        if (fireStation != null) {

            FireStation currentFireStation = fireStation;
            String address = fireStationToUpdate.getAddress();
            String station = fireStationToUpdate.getStation();

            if (address != null) {
                currentFireStation.setAddress(address);
            }

            if (station != null) {
                currentFireStation.setStation(station);
            }

            fireStationService.updateFireStation(currentFireStation);
            return currentFireStation;

        } else {
            return null;

        }
    }

    @DeleteMapping("/firestation/{id}")
    public void deleteFireStation(@PathVariable("id") final String id) {
        fireStationService.deleteFireStation(id);
    }

}
