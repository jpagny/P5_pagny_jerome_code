package com.safetynet.alerts.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "firestations")
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String station;

}
