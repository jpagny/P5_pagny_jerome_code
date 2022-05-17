package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStationModel;

import java.util.Optional;

public interface IFireStationService {

    FireStationModel getFireStation(final String id);

    Iterable<FireStationModel> getFireStations();

    Optional<FireStationModel> getFireStationByAddress(final String address);

    Iterable<FireStationModel> getFireStationsByStationNumber(final int stationNumber);

    FireStationModel saveFireStation(FireStationModel fireStationModel);

    FireStationModel updateFireStation(FireStationModel fireStationModel);

    void deleteFireStation(final String id);
}
