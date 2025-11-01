package org.securityapps.vehicleregistry.outbound.persistence.adapter;

import org.securityapps.vehicleregistry.common.exception.ResourceNotFoundException;
import org.securityapps.vehicleregistry.outbound.persistence.entity.VehicleEntity;
import org.securityapps.vehicleregistry.outbound.persistence.mapper.VehicleMapper;
import org.securityapps.vehicleregistry.outbound.persistence.repository.JpaVehicleRepository;
import org.securityapps.vehicleregistry.domain.vehicle.Vehicle;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleId;
import org.securityapps.vehicleregistry.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public class VehicleRepositoryImpl implements VehicleRepository {
    private final JpaVehicleRepository jpaVehicleRepository;
    private final VehicleMapper vehicleMapper;

    public  VehicleRepositoryImpl(JpaVehicleRepository jpaVehicleRepository, VehicleMapper vehicleMapper) {
        this.jpaVehicleRepository = jpaVehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleEntity entity=vehicleMapper.toEntity(vehicle);
        VehicleEntity savedEntity=jpaVehicleRepository.save(entity);
        return vehicleMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Vehicle> findById(VehicleId id) {
        VehicleEntity entity=jpaVehicleRepository.findById(id.toString()).orElseThrow(()->new ResourceNotFoundException("Vehicle not found"));
        return Optional.ofNullable(vehicleMapper.toDomain(entity));
    }
    @Override
    public Optional<Vehicle> findByPlateNumber(String plateNumber) {
       VehicleEntity entity=jpaVehicleRepository.findVehicleByPlateNumber(plateNumber).orElseThrow(()->new ResourceNotFoundException("Vehicle not found"));
       return Optional.ofNullable(vehicleMapper.toDomain(entity));
    }
    @Override
    public Optional<Vehicle> findByVin(String vin) {
        VehicleEntity entity= jpaVehicleRepository.findVehicleByVin(vin).orElseThrow(()->new ResourceNotFoundException("Vehicle not found"));
        return Optional.ofNullable(vehicleMapper.toDomain(entity));
    }
}
