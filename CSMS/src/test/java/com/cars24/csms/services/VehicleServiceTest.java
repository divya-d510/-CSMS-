package com.cars24.csms.services;

import com.cars24.csms.data.dao.impl.VehicleDaoImpl;
import com.cars24.csms.data.entities.CustomerEntity;
import com.cars24.csms.data.entities.VehicleEntity;
import com.cars24.csms.data.req.CreateVehicleReq;
import com.cars24.csms.data.req.GetVehicleReq;
import com.cars24.csms.data.req.UpdateVehicleReq;
import com.cars24.csms.data.res.CreateVehicleRes;
import com.cars24.csms.data.res.GetVehicleRes;
import com.cars24.csms.data.res.UpdateVehicleRes;
import com.cars24.csms.repositories.CustomerRepository;
import com.cars24.csms.services.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleDaoImpl vehicleDaoImpl;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private CreateVehicleReq createVehicleReq;


    private UpdateVehicleReq updateVehicleReq;
    private UpdateVehicleRes updateVehicleRes;

    private VehicleEntity vehicleEntity;


    @BeforeEach
    void setUp() {
        createVehicleReq = new CreateVehicleReq();
        createVehicleReq.setVehicleId(101);
        createVehicleReq.setCustomerId(1);
        createVehicleReq.setLicensePlate("AB123CD");
        createVehicleReq.setModel("Sedan");
        createVehicleReq.setMake("Toyota");
        createVehicleReq.setYear(2022);
        createVehicleReq.setColor("Red");

        updateVehicleReq = new UpdateVehicleReq();
        updateVehicleReq.setModel("UpdatedModel");
        updateVehicleReq.setMake("UpdatedMake");
        updateVehicleReq.setYear(2023);
        updateVehicleReq.setColor("Blue");
    }

    @Test
    void testCreateVehicle_Success() {
        // Mocking customer found in the database
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomer_id(1);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customerEntity));

        // Call the method
        CreateVehicleRes response = vehicleService.createVehicle(createVehicleReq);

        // Verify that vehicleDaoImpl.createVehicle was called
        verify(vehicleDaoImpl, times(1)).createVehicle(createVehicleReq);

        // Validate the response
        assertNotNull(response);
        assertEquals(createVehicleReq.getVehicleId(), response.getVehicleId());
        assertEquals(createVehicleReq.getCustomerId(), response.getCustomerId());
        assertEquals(createVehicleReq.getLicensePlate(), response.getLicensePlate());
        assertEquals(createVehicleReq.getModel(), response.getModel());
        assertEquals(createVehicleReq.getMake(), response.getMake());
        assertEquals(createVehicleReq.getYear(), response.getYear());
        assertEquals(createVehicleReq.getColor(), response.getColor());
    }

    @Test
    void testCreateVehicle_CustomerNotFound() {
        // Mocking customer not found
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Call the method
        CreateVehicleRes response = vehicleService.createVehicle(createVehicleReq);

        // Verify that vehicleDaoImpl.createVehicle was never called
        verify(vehicleDaoImpl, never()).createVehicle(any());

        // Assert that response is null (customer not found)
        assertNull(response);
    }

//    @Test
//    public void testGetVehicle(){
//        GetVehicleReq getVehicleReq = new GetVehicleReq();
//        getVehicleReq.setModel("Swift");
//        getVehicleReq.setColor("Red");
//        List<GetVehicleRes> getVehicleRes = vehicleService.getVehicle(getVehicleReq);
//        System.out.println(getVehicleRes);
//        // Assert
//        assertNotNull(getVehicleRes); // Ensure the response is not null
//        assertFalse(getVehicleRes.isEmpty()); // Ensure the list is not empty
//
//        // Checking the first vehicle's color
//        assertEquals(getVehicleReq.getColor(), getVehicleRes.get(0).getColor());
//
//        // Print the result for debugging
//        System.out.println(getVehicleRes);
//    }

    @Test
    void testUpdateVehicle_Success() {
        // Arrange
        String licensePlate = "AB123CD";
        UpdateVehicleRes updateVehicleRes = new UpdateVehicleRes();
        updateVehicleRes.setLicensePlate(licensePlate);
        updateVehicleRes.setModel(updateVehicleReq.getModel());
        updateVehicleRes.setMake(updateVehicleReq.getMake());
        updateVehicleRes.setYear(updateVehicleReq.getYear());
        updateVehicleRes.setColor(updateVehicleReq.getColor());

        // Mock the behavior of vehicleDaoImpl.updateVehicle
        when(vehicleDaoImpl.updateVehicle(eq(licensePlate), eq(updateVehicleReq)))
                .thenReturn(updateVehicleRes);

        // Act
        UpdateVehicleRes result = vehicleService.updateVehicle(licensePlate, updateVehicleReq);

        // Assert
        assertNotNull(result);
        assertEquals(licensePlate, result.getLicensePlate());
        assertEquals(updateVehicleReq.getModel(), result.getModel());
        assertEquals(updateVehicleReq.getMake(), result.getMake());
        assertEquals(updateVehicleReq.getYear(), result.getYear());
        assertEquals(updateVehicleReq.getColor(), result.getColor());

        // Verify that vehicleDaoImpl.updateVehicle was called with the correct arguments
        verify(vehicleDaoImpl, times(1)).updateVehicle(eq(licensePlate), eq(updateVehicleReq));
    }

    @Test
    void testUpdateVehicle_VehicleNotFound() {
        // Arrange
        String licensePlate = "AB123CD";

        // Mock the behavior of vehicleDaoImpl.updateVehicle to return null (simulating not found)
        when(vehicleDaoImpl.updateVehicle(eq(licensePlate), eq(updateVehicleReq)))
                .thenReturn(null);

        // Act
        UpdateVehicleRes result = vehicleService.updateVehicle(licensePlate, updateVehicleReq);

        // Assert
        assertNull(result, "The vehicle should not be found, and the result should be null.");

        // Verify that vehicleDaoImpl.updateVehicle was called with the correct arguments
        verify(vehicleDaoImpl, times(1)).updateVehicle(eq(licensePlate), eq(updateVehicleReq));
    }

    @Test
    void testDeleteVehicle_Success() {
        // Arrange
        String licensePlate = "AB123CD";

        // Mocking the behavior of the vehicleDaoImpl.deleteVehicle method
        when(vehicleDaoImpl.deleteVehicle(licensePlate)).thenReturn("Vehicle with license plate " + licensePlate + " deleted successfully");

        // Act
        String result = vehicleService.deleteVehicleByLicensePlate(licensePlate);

        // Assert
        assertNotNull(result);
        assertEquals("Vehicle with license plate " + licensePlate + " deleted successfully", result);

        // Verify that deleteVehicle was called with the correct license plate
        verify(vehicleDaoImpl, times(1)).deleteVehicle(licensePlate);
    }

    @Test
    void testDeleteVehicle_VehicleNotFound() {
        // Arrange
        String licensePlate = "XYZ999";

        // Mocking the behavior of the vehicleDaoImpl.deleteVehicle method
        when(vehicleDaoImpl.deleteVehicle(licensePlate)).thenReturn("Vehicle with license plate " + licensePlate + " not found");

        // Act
        String result = vehicleService.deleteVehicleByLicensePlate(licensePlate);

        // Assert
        assertNotNull(result);
        assertEquals("Vehicle with license plate " + licensePlate + " not found", result);

        // Verify that deleteVehicle was called with the correct license plate
        verify(vehicleDaoImpl, times(1)).deleteVehicle(licensePlate);
    }
}

