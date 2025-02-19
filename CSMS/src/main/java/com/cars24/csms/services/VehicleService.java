package com.cars24.csms.services;

import com.cars24.csms.data.req.CreateVehicleReq;
import com.cars24.csms.data.req.GetVehicleReq;
import com.cars24.csms.data.req.UpdateVehicleReq;
import com.cars24.csms.data.res.CreateVehicleRes;
import com.cars24.csms.data.res.GetVehicleRes;
import com.cars24.csms.data.res.UpdateVehicleRes;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface VehicleService {

    CreateVehicleRes createVehicle(CreateVehicleReq createVehicleReq);
    List<GetVehicleRes> getVehicle(GetVehicleReq getVehicleReq);
    GetVehicleRes getVehicleById(int vehicleId);
    UpdateVehicleRes updateVehicle(String licensePlate, UpdateVehicleReq updateVehicleReq);
    String deleteVehicleByLicensePlate(String licensePlate);
}
