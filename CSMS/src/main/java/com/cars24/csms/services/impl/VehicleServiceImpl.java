package com.cars24.csms.services.impl;

import com.cars24.csms.data.dao.impl.VehicleDaoImpl;
import com.cars24.csms.data.entities.VehicleEntity;
import com.cars24.csms.data.req.CreateVehicleReq;
import com.cars24.csms.data.req.GetVehicleReq;
import com.cars24.csms.data.req.UpdateVehicleReq;
import com.cars24.csms.data.res.CreateVehicleRes;
import com.cars24.csms.data.res.GetVehicleRes;
import com.cars24.csms.data.res.UpdateVehicleRes;
import com.cars24.csms.repositories.CustomerRepository;
import com.cars24.csms.data.entities.CustomerEntity;

import com.cars24.csms.repositories.VehicleRepository;
import com.cars24.csms.services.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleDaoImpl vehicleDaoImpl;

    private final CustomerRepository customerRepository;

    private final VehicleRepository vehicleRepository;

    public CreateVehicleRes createVehicle(CreateVehicleReq createVehicleReq) {
            CustomerEntity customer = customerRepository.findById(createVehicleReq.getCustomerId()).orElse(null);
            if (customer == null) {
                log.error("[createVehicle] Customer with ID {} not found", createVehicleReq.getCustomerId());
                return null;
            }

            vehicleDaoImpl.createVehicle(createVehicleReq); // Call to DAO layer to save vehicle
            log.info("[createVehicle] Vehicle created: {}", createVehicleReq);

            // Create and return a response
            CreateVehicleRes response = new CreateVehicleRes();
            response.setVehicleId(createVehicleReq.getVehicleId());
            response.setCustomerId(createVehicleReq.getCustomerId());
            response.setLicensePlate(createVehicleReq.getLicensePlate());
            response.setModel(createVehicleReq.getModel());
            response.setMake(createVehicleReq.getMake());
            response.setYear(createVehicleReq.getYear());
            response.setColor(createVehicleReq.getColor());

            return response;
    }


    public List<GetVehicleRes> getVehicle(GetVehicleReq getVehicleReq) {
        return vehicleDaoImpl.getVehicle(getVehicleReq);
    }

    public GetVehicleRes getVehicleById(int vehicleId) {
        return vehicleDaoImpl.getVehicleById(vehicleId);
    }

    public UpdateVehicleRes updateVehicle(String licensePlate, UpdateVehicleReq updateVehicleReq) {
        return vehicleDaoImpl.updateVehicle(licensePlate, updateVehicleReq);
    }

    public String deleteVehicleByLicensePlate(String licensePlate) {
        return vehicleDaoImpl.deleteVehicle(licensePlate);
    }

}
