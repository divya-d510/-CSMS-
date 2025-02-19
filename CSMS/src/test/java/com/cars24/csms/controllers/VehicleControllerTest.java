package com.cars24.csms.controllers;

import com.cars24.csms.data.req.CreateVehicleReq;
import com.cars24.csms.data.req.UpdateVehicleReq;
import com.cars24.csms.data.res.CreateVehicleRes;
import com.cars24.csms.data.res.UpdateVehicleRes;
import com.cars24.csms.services.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class VehicleControllerTest {

    @Mock
    private VehicleServiceImpl vehicleServiceImpl;

    @InjectMocks
    private VehicleController vehicleController;

    private CreateVehicleReq createVehicleReq;
    private UpdateVehicleReq updateVehicleReq;

    @BeforeEach
    void setUp() {
        // Initialize the request objects for testing
        createVehicleReq = new CreateVehicleReq();
        createVehicleReq.setColor("Red");
        createVehicleReq.setCustomerId(1);
        createVehicleReq.setLicensePlate("XYZ123");
        createVehicleReq.setMake("Toyota");
        createVehicleReq.setModel("Corolla");
        createVehicleReq.setVehicleId(1);
        createVehicleReq.setYear(2022);

        updateVehicleReq = new UpdateVehicleReq();
        updateVehicleReq.setColor("Blue");
        updateVehicleReq.setMake("Honda");
        updateVehicleReq.setModel("Civic");
        updateVehicleReq.setYear(2023);
    }

    @Test
    void testCreateVehicle() {
        // Arrange
        CreateVehicleRes expectedResponse = new CreateVehicleRes();
        expectedResponse.setLicensePlate("XYZ123");
        when(vehicleServiceImpl.createVehicle(createVehicleReq)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<CreateVehicleRes> response = vehicleController.createVehicle(createVehicleReq);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("XYZ123", response.getBody().getLicensePlate());
        verify(vehicleServiceImpl, times(1)).createVehicle(createVehicleReq);
    }

    @Test
    void testUpdateVehicle() {
        // Arrange
        UpdateVehicleRes expectedResponse = new UpdateVehicleRes();
        expectedResponse.setLicensePlate("XYZ123");
        expectedResponse.setMake("Honda");
        when(vehicleServiceImpl.updateVehicle("XYZ123", updateVehicleReq)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<UpdateVehicleRes> response = vehicleController.updateVehicle("XYZ123", updateVehicleReq);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Honda", response.getBody().getMake());
        verify(vehicleServiceImpl, times(1)).updateVehicle("XYZ123", updateVehicleReq);
    }

    @Test
    void testUpdateVehicle_NotFound() {
        // Arrange
        when(vehicleServiceImpl.updateVehicle("XYZ123", updateVehicleReq)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<UpdateVehicleRes> response = vehicleController.updateVehicle("XYZ123", updateVehicleReq);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(vehicleServiceImpl, times(1)).updateVehicle("XYZ123", updateVehicleReq);
    }

    @Test
    void testDeleteVehicle() {
        // Arrange
        String licensePlate = "XYZ123";
        String expectedResponse = "Vehicle with license plate XYZ123 deleted successfully";
        when(vehicleServiceImpl.deleteVehicleByLicensePlate(licensePlate)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = vehicleController.deleteVehicle(licensePlate);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(vehicleServiceImpl, times(1)).deleteVehicleByLicensePlate(licensePlate);
    }
}
