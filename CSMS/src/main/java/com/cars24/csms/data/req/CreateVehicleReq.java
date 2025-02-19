package com.cars24.csms.data.req;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateVehicleReq {

    @Min(value = 1, message = "Invalid vehicleId")
    private int vehicleId;

    @Min(value = 1, message = "Invalid customerId")
    private int customerId;

    @NotBlank(message = "License Plate cannot be blank")
    @NotNull(message = "License Plate cannot be null")
    private String licensePlate;

    private String model;

    private String make;

    @Min(value = 1900, message = "Year must be greater than 1900")
    @Max(value = 2025, message = "Year must be less than or equal to 2025")
    private int year;

    private String color;



}
