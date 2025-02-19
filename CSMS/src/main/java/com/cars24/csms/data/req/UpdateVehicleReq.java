package com.cars24.csms.data.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.Data;

@Data
public class UpdateVehicleReq {


    private String model;

    private String make;

    @Min(value = 1900, message = "Year must be greater than 1900")
    @Max(value = 2025, message = "Year must be less than or equal to 2025")
    private int year;

    private String color;
}
