package com.safetynet.alerts.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class FireStation {

    @Id
    private String id;

    private String address;

    private String station;

}
