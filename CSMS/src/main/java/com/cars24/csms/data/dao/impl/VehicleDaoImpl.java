package com.cars24.csms.data.dao.impl;

import com.cars24.csms.data.dao.VehicleDao;
import com.cars24.csms.data.req.CreateVehicleReq;
import com.cars24.csms.data.entities.VehicleEntity;
import com.cars24.csms.data.req.GetVehicleReq;
import com.cars24.csms.data.req.UpdateVehicleReq;
import com.cars24.csms.data.res.GetVehicleRes;
import com.cars24.csms.data.res.UpdateVehicleRes;
import com.cars24.csms.repositories.CustomerRepository;
import com.cars24.csms.repositories.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleDaoImpl implements VehicleDao{
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

     public int createVehicle(CreateVehicleReq createVehicleReq){

        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVehicleId(0);

        vehicleEntity.setCustomerId(createVehicleReq.getCustomerId());
        vehicleEntity.setLicensePlate(createVehicleReq.getLicensePlate());
        vehicleEntity.setModel(createVehicleReq.getModel());
        vehicleEntity.setMake(createVehicleReq.getMake());
        vehicleEntity.setYear(createVehicleReq.getYear());
        vehicleEntity.setColor(createVehicleReq.getColor());

        vehicleRepository.save(vehicleEntity);

        return 0;
    }

    public GetVehicleRes getVehicleById(int vehicleId) {
        Optional<VehicleEntity> vehicleOptional = vehicleRepository.findById(vehicleId);

        return vehicleOptional.map(vehicle -> new GetVehicleRes(
                vehicle.getVehicleId(),
                vehicle.getCustomerId(),
                vehicle.getLicensePlate(),
                vehicle.getModel(),
                vehicle.getMake(),
                vehicle.getYear(),
                vehicle.getColor()
        )).orElseThrow(() -> new EntityNotFoundException("Vehicle not found with ID: " + vehicleId));
    }

    public List<GetVehicleRes> getVehicle(GetVehicleReq getVehicleReq){
        // Fetch vehicles based on the provided model and/or color
        List<VehicleEntity> vehicleEntities;


        if (getVehicleReq.getModel() != null && getVehicleReq.getColor() != null) {
            vehicleEntities = vehicleRepository.findByModelAndColorAndIsActiveTrue(getVehicleReq.getModel(), getVehicleReq.getColor());
        } else if (getVehicleReq.getModel() != null) {
            vehicleEntities = vehicleRepository.findByModelAndIsActiveTrue(getVehicleReq.getModel());
        } else if (getVehicleReq.getColor() != null) {
            vehicleEntities = vehicleRepository.findByColorAndIsActiveTrue(getVehicleReq.getColor());
        } else {
            vehicleEntities = vehicleRepository.findByIsActiveTrue();
        }

        if (vehicleEntities == null) {
            return Collections.emptyList();
        }


        return vehicleEntities.stream().map(vehicle -> {
            GetVehicleRes vehicleRes = new GetVehicleRes(vehicle.getVehicleId(), vehicle.getCustomerId(), vehicle.getLicensePlate(), vehicle.getModel(), vehicle.getMake(), vehicle.getYear(), vehicle.getColor());
            vehicleRes.setVehicleId(vehicle.getVehicleId());
            vehicleRes.setCustomerId(vehicle.getCustomerId());
            vehicleRes.setLicensePlate(vehicle.getLicensePlate());
            vehicleRes.setModel(vehicle.getModel());
            vehicleRes.setMake(vehicle.getMake());
            vehicleRes.setYear(vehicle.getYear());
            vehicleRes.setColor(vehicle.getColor());
            return vehicleRes;
        }).collect(Collectors.toList());

    }


    public UpdateVehicleRes updateVehicle(String licensePlate, UpdateVehicleReq updateVehicleReq) {
        // Find the vehicle by license plate
        VehicleEntity vehicleEntity = vehicleRepository.findByLicensePlate(licensePlate);

        // Check if the vehicle exists
        if (vehicleEntity == null) {
            throw new RuntimeException("Vehicle not found with license plate: " + licensePlate);
        }

        // Update the fields in the entity based on the request
        if (updateVehicleReq.getModel() != null) {
            vehicleEntity.setModel(updateVehicleReq.getModel());
        }
        if (updateVehicleReq.getMake() != null) {
            vehicleEntity.setMake(updateVehicleReq.getMake());
        }
        if (updateVehicleReq.getYear() != 0) {
            vehicleEntity.setYear(updateVehicleReq.getYear());
        }
        if (updateVehicleReq.getColor() != null) {
            vehicleEntity.setColor(updateVehicleReq.getColor());
        }

        // Save the updated vehicle entity
        vehicleRepository.save(vehicleEntity);

        // Return the updated details as a response
        UpdateVehicleRes updateVehicleRes = new UpdateVehicleRes();
        updateVehicleRes.setVehicleId(vehicleEntity.getVehicleId());
        updateVehicleRes.setCustomerId(vehicleEntity.getCustomerId());
        updateVehicleRes.setLicensePlate(vehicleEntity.getLicensePlate());
        updateVehicleRes.setModel(vehicleEntity.getModel());
        updateVehicleRes.setMake(vehicleEntity.getMake());
        updateVehicleRes.setYear(vehicleEntity.getYear());
        updateVehicleRes.setColor(vehicleEntity.getColor());

        return updateVehicleRes;
    }


    public String deleteVehicle(String licensePlate) {
        Optional<VehicleEntity> vehicle = vehicleRepository.findByLicensePlateAndIsActiveTrue(licensePlate);

        if (vehicle.isPresent()) {
            VehicleEntity entity = vehicle.get();
            entity.setActive(false); // Mark as inactive
            vehicleRepository.save(entity);
            return "Vehicle with license plate " + licensePlate + " has been soft-deleted.";
        } else {
            return "Active vehicle with license plate " + licensePlate + " does not exist.";
        }
    }

}
