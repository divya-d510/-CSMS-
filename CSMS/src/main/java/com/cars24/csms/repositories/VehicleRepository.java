package com.cars24.csms.repositories;

import com.cars24.csms.data.entities.VehicleEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer> {
    // Fetch active vehicles by model and color
    List<VehicleEntity> findByModelAndColorAndIsActiveTrue(String model, String color);

    // Fetch active vehicles by model
    List<VehicleEntity> findByModelAndIsActiveTrue(String model);

    // Fetch active vehicles by color
    List<VehicleEntity> findByColorAndIsActiveTrue(String color);

    // Fetch active vehicles
    List<VehicleEntity> findByIsActiveTrue();

    // Find a vehicle by license plate if it's active
    Optional<VehicleEntity> findByLicensePlateAndIsActiveTrue(String licensePlate);

    VehicleEntity findByLicensePlate(String licensePlate);
}