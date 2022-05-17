package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStationModel;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/firestations")
    public Iterable<FireStationModel> getFireStations() {
        return fireStationService.getFireStations();
    }

    @GetMapping("/firestation/{id}")
    public FireStationModel getFireStation(@PathVariable final String id) {
        return fireStationService.getFireStation(id);
    }

    @PostMapping("/firestation")
    public FireStationModel createPerson(@RequestBody FireStationModel fireStationModelToSave) {
        return fireStationService.saveFireStation(fireStationModelToSave);
    }

    @PutMapping("/firestation/{id}")
    public FireStationModel updateFireStation(@PathVariable("id") final String id, @RequestBody FireStationModel fireStationModelToUpdate) {
        FireStationModel fireStationModel = fireStationService.getFireStation(id);

        if (fireStationModel != null) {

            String address = fireStationModelToUpdate.getAddress() == null ? fireStationModel.getAddress() : fireStationModelToUpdate.getAddress();
            String station = fireStationModelToUpdate.getStation() == null ? fireStationModel.getStation() : fireStationModelToUpdate.getStation();

            FireStationModel fireStationModelUpdated = new FireStationModel(id, address, station);

            fireStationService.updateFireStation(fireStationModelUpdated);
            return fireStationModelUpdated;

        } else {
            return null;

        }
    }

    @DeleteMapping("/firestation/{id}")
    public void deleteFireStation(@PathVariable("id") final String id) {
        fireStationService.deleteFireStation(id);
    }

}
