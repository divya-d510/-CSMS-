package com.cars24.csms.data.res;


import lombok.Data;

@Data
public class GetVehicleRes {
    private int vehicleId;

    private int customerId;

    private String licensePlate;

    private String model;

    private String make;

    private int year;

    private String color;

    public GetVehicleRes(int vehicleId, int customerId, String licensePlate, String model, String make, int year, String color) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.licensePlate = licensePlate;
        this.model = model;
        this.make = make;
        this.year = year;
        this.color = color;
    }
}
