package com.safetynet.alerts.service;

import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FireStationService {

    @Autowired
    private DataFromJsonFile data;

    public FireStation getFireStation(final String id) {
        return data.getFireStations().get(id);
    }

    public Iterable<FireStation> getFireStations() {
        return data.getFireStations().values();
    }

    public Optional<FireStation> getFireStationByAddress(final String address) {
        return data.getFireStations().values().stream()
                .filter(theFireStation -> address.equalsIgnoreCase(theFireStation.getAddress()))
                .findFirst();
    }

    public Iterable<FireStation> getFireStationsByStationNumber(final int stationNumber) {
        return data.getFireStations().values().stream()
                .filter(theFireStation -> String.valueOf(stationNumber).equalsIgnoreCase(theFireStation.getStation()))
                .collect(Collectors.toList());
    }

    public FireStation saveFireStation(FireStation fireStation) {
        String uniqueID = UUID.randomUUID().toString();
        data.getFireStations().put(uniqueID, fireStation);
        return data.getFireStations().get(uniqueID);
    }

    public FireStation updateFireStation(FireStation fireStation){
        data.getFireStations().put(fireStation.getId(),fireStation);
        return fireStation;
    }

    public void deleteFireStation(final String id) {
        data.getFireStations().remove(id);
    }
}
