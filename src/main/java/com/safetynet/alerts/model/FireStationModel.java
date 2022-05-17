package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FireStationModel {


    private String id;
    private String address;
    private String station;

    public FireStationModel() {
    }

    public FireStationModel(String theAddress, String theStation) {
        id = UUID.randomUUID().toString();
        address = theAddress;
        station = theStation;
    }

}
