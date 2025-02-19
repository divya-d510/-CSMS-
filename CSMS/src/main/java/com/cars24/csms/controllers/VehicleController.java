package com.cars24.csms.controllers;

import com.cars24.csms.data.req.CreateVehicleReq;
import com.cars24.csms.data.req.GetVehicleReq;
import com.cars24.csms.data.req.UpdateVehicleReq;
import com.cars24.csms.data.res.CreateVehicleRes;
import com.cars24.csms.data.res.GetVehicleRes;
import com.cars24.csms.data.res.UpdateVehicleRes;
import com.cars24.csms.services.impl.VehicleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vehicle")
@Validated
@Slf4j
@RequiredArgsConstructor
@Service
public class VehicleController {

    private final VehicleServiceImpl vehicleServiceImpl;

    //private static final Logger log = LoggerFactory.getLogger(VehicleController.class);


    @PostMapping
    public ResponseEntity<CreateVehicleRes> createVehicle(@Valid @RequestBody CreateVehicleReq createVehicleReq) {
        //System.out.println("[createVehicle] createVehicleReq {}"+ createVehicleReq);
        log.info("[createVehicle] createVehicleReq {}", createVehicleReq);

        // Call service layer to process the request
        CreateVehicleRes createVehicleRes = vehicleServiceImpl.createVehicle(createVehicleReq);

        // Return the response
        return ResponseEntity.ok().body(createVehicleRes);

    }

    @GetMapping
    public ResponseEntity<List<GetVehicleRes>> getVehicle(@RequestParam(required = false) String model,
                                                           @RequestParam(required = false) String color) {
        GetVehicleReq getVehicleReq = new GetVehicleReq();
        getVehicleReq.setModel(model);
        getVehicleReq.setColor(color);

        List<GetVehicleRes> vehicles = vehicleServiceImpl.getVehicle(getVehicleReq); // Call the service layer
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{vehicleId}")
    public GetVehicleRes getVehicleById(@PathVariable int vehicleId) {
        return vehicleServiceImpl.getVehicleById(vehicleId);
    }

    @PutMapping("/{licensePlate}")
    public ResponseEntity<UpdateVehicleRes> updateVehicle(@PathVariable String licensePlate,
                                                          @RequestBody UpdateVehicleReq updateVehicleReq) {
        try {
            // Pass the license plate and update request to the service
            UpdateVehicleRes updatedVehicle = vehicleServiceImpl.updateVehicle(licensePlate, updateVehicleReq);
            return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
        } catch (RuntimeException e) {
            // If the vehicle is not found or other issues occur, return a NOT_FOUND response
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/license/{licensePlate}")
    public ResponseEntity<String> deleteVehicle(@PathVariable String licensePlate) {
        String result = vehicleServiceImpl.deleteVehicleByLicensePlate(licensePlate);
        return ResponseEntity.ok(result);
    }




}

