package com.cars24.csms.data.entities;


import jakarta.persistence.*;

import lombok.Data;



@Data
@Entity
@Table(name="vehicles")
public class VehicleEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vehicle_id")
    private int vehicleId;

    //@ManyToOne
    //@JoinColumn(name="customer_id", nullable = false)
    @Column(name="customer_id", nullable = false)
    private int customerId;


    @Column(name="license_plate", nullable = false)
    private String licensePlate;

    @Column(name="model")
    private String model;

    @Column(name="make")
    private String make;

    @Column(name="year")
    private int year;

    @Column(name="color")
    private String color;

    @Column(name="is_active",nullable=false)
    private boolean isActive = true;
}
