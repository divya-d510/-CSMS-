package com.cars24.csms.data.dao;


import com.cars24.csms.data.req.CreateVehicleReq;
import com.cars24.csms.data.req.GetVehicleReq;
import com.cars24.csms.data.req.UpdateVehicleReq;
import com.cars24.csms.data.res.GetVehicleRes;
import com.cars24.csms.data.res.UpdateVehicleRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleDao {
    int createVehicle(CreateVehicleReq createVehicleReq);
    List<GetVehicleRes> getVehicle(GetVehicleReq getVehicleReq);// retrieve the vehicles based on model or color
    GetVehicleRes getVehicleById(int vehicleId);
    UpdateVehicleRes updateVehicle(String licensePlate,UpdateVehicleReq updateVehicleReq); //update the details based on make or model
    String deleteVehicle(String licensePlate);
}
