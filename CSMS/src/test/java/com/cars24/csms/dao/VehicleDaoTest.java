package com.cars24.csms.dao;

import com.cars24.csms.data.dao.impl.VehicleDaoImpl;
import com.cars24.csms.data.entities.VehicleEntity;
import com.cars24.csms.data.req.CreateVehicleReq;
import com.cars24.csms.data.req.GetVehicleReq;
import com.cars24.csms.data.req.UpdateVehicleReq;
import com.cars24.csms.data.res.GetVehicleRes;
import com.cars24.csms.data.res.UpdateVehicleRes;
import com.cars24.csms.repositories.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

class VehicleDaoTest {

    @InjectMocks
    private VehicleDaoImpl vehicleDao;

    @Mock
    private VehicleRepository vehicleRepository;

    private CreateVehicleReq createVehicleReq;
    private GetVehicleReq getVehicleReq;
    private UpdateVehicleReq updateVehicleReq;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setting up mock requests
        createVehicleReq = new CreateVehicleReq();
        createVehicleReq.setCustomerId(1);
        createVehicleReq.setLicensePlate("XYZ123");
        createVehicleReq.setModel("Model1");
        createVehicleReq.setMake("Make1");
        createVehicleReq.setYear(2020);
        createVehicleReq.setColor("Red");

        getVehicleReq = new GetVehicleReq();
        getVehicleReq.setModel("Model1");
        getVehicleReq.setColor("Red");

        updateVehicleReq = new UpdateVehicleReq();
        updateVehicleReq.setModel("UpdatedModel");
        updateVehicleReq.setMake("UpdatedMake");
        updateVehicleReq.setYear(2021);
        updateVehicleReq.setColor("Blue");
    }

    @Test
    void testCreateVehicle() {
        // Arrange
        when(vehicleRepository.save(any())).thenReturn(null); // No need to return anything for save

        // Act
        int result = vehicleDao.createVehicle(createVehicleReq);

        // Assert
        assertEquals(0, result);
        verify(vehicleRepository, times(1)).save(any()); // Ensure save was called once
    }

    @Test
    void testGetVehicle_Success() {
        // Arrange
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVehicleId(1);
        vehicleEntity.setCustomerId(1);
        vehicleEntity.setLicensePlate("XYZ123");
        vehicleEntity.setModel("Model1");
        vehicleEntity.setMake("Make1");
        vehicleEntity.setYear(2020);
        vehicleEntity.setColor("Red");

        when(vehicleRepository.findByModelAndColorAndIsActiveTrue("Model1", "Red"))
                .thenReturn(List.of(vehicleEntity));

        // Act
        List<GetVehicleRes> result = vehicleDao.getVehicle(getVehicleReq);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("XYZ123", result.get(0).getLicensePlate());
    }

    @Test
    void testUpdateVehicle_Success() {
        // Arrange
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVehicleId(1);
        vehicleEntity.setLicensePlate("XYZ123");
        vehicleEntity.setModel("OldModel");
        vehicleEntity.setMake("OldMake");
        vehicleEntity.setYear(2020);
        vehicleEntity.setColor("Red");

        when(vehicleRepository.findByLicensePlate("XYZ123")).thenReturn(vehicleEntity);
        when(vehicleRepository.save(vehicleEntity)).thenReturn(vehicleEntity);

        // Act
        UpdateVehicleRes result = vehicleDao.updateVehicle("XYZ123", updateVehicleReq);

        // Assert
        assertNotNull(result);
        assertEquals("UpdatedModel", result.getModel());
        assertEquals("UpdatedMake", result.getMake());
        verify(vehicleRepository, times(1)).save(vehicleEntity); // Ensure save is called
    }

    @Test
    void testUpdateVehicle_NotFound() {
        // Arrange
        when(vehicleRepository.findByLicensePlate("XYZ123")).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            vehicleDao.updateVehicle("XYZ123", updateVehicleReq);
        });
        assertTrue(exception.getMessage().contains("Vehicle not found with license plate"));
    }

    @Test
    void testDeleteVehicle_Success() {
        // Arrange
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVehicleId(1);
        vehicleEntity.setLicensePlate("XYZ123");
        vehicleEntity.setModel("Model1");
        vehicleEntity.setMake("Make1");
        vehicleEntity.setYear(2020);
        vehicleEntity.setColor("Red");
        vehicleEntity.setActive(true);

        when(vehicleRepository.findByLicensePlateAndIsActiveTrue("XYZ123"))
                .thenReturn(Optional.of(vehicleEntity));

        // Act
        String result = vehicleDao.deleteVehicle("XYZ123");

        // Assert
        assertEquals("Vehicle with license plate XYZ123 has been soft-deleted.", result);
        verify(vehicleRepository, times(1)).save(vehicleEntity); // Ensure save is called for soft deletion
    }

    @Test
    void testDeleteVehicle_NotFound() {
        // Arrange
        when(vehicleRepository.findByLicensePlateAndIsActiveTrue("XYZ123"))
                .thenReturn(Optional.empty());

        // Act
        String result = vehicleDao.deleteVehicle("XYZ123");

        // Assert
        assertEquals("Active vehicle with license plate XYZ123 does not exist.", result);
    }
}
