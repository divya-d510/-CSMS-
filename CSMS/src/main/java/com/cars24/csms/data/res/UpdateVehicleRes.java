package com.cars24.csms.data.res;

import lombok.Data;

@Data
public class UpdateVehicleRes {
    private int vehicleId;

    private int customerId;

    private String licensePlate;

    private String model;

    private String make;

    private int year;

    private String color;

}
