package com.safetynet.alerts.service;

import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.FireStationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationService implements IFireStationService {

    @Autowired
    private DataFromJsonFile data;

    public FireStationModel getFireStation(final String id) {
        return data.getFireStations().get(id);
    }

    public Iterable<FireStationModel> getFireStations() {
        return data.getFireStations().values();
    }

    public Optional<FireStationModel> getFireStationByAddress(final String address) {
        return data.getFireStations().values().stream()
                .filter(theFireStation -> address.equalsIgnoreCase(theFireStation.getAddress()))
                .findFirst();
    }

    public Iterable<FireStationModel> getFireStationsByStationNumber(final int stationNumber) {
        return data.getFireStations().values().stream()
                .filter(theFireStation -> String.valueOf(stationNumber).equalsIgnoreCase(theFireStation.getStation()))
                .collect(Collectors.toList());
    }

    public FireStationModel saveFireStation(FireStationModel fireStationModel) {
        data.getFireStations().put(fireStationModel.getId(), fireStationModel);
        return data.getFireStations().get(fireStationModel.getId());
    }

    public FireStationModel updateFireStation(FireStationModel fireStationModel) {
        data.getFireStations().put(fireStationModel.getId(), fireStationModel);
        return fireStationModel;
    }

    public void deleteFireStation(final String id) {
        data.getFireStations().remove(id);
    }
}
