package org.securityapps.vehicleregistry.outbound.persistence.adapter;

import org.securityapps.vehicleregistry.common.exception.ResourceNotFoundException;
import org.securityapps.vehicleregistry.outbound.persistence.entity.DriverEntity;
import org.securityapps.vehicleregistry.outbound.persistence.mapper.DriverMapper;
import org.securityapps.vehicleregistry.outbound.persistence.repository.JpaDriverRepository;
import org.securityapps.vehicleregistry.domain.driver.Driver;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.driver.DriverRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public class DriverRepositoryImpl implements DriverRepository {
    private final JpaDriverRepository jpaDriverRepository;
    private final DriverMapper driverMapper;

    public DriverRepositoryImpl(JpaDriverRepository jpaDriverRepository,DriverMapper driverMapper) {
        this.jpaDriverRepository = jpaDriverRepository;
        this.driverMapper = driverMapper;
    }

    public Driver save(Driver driver){
        DriverEntity entity= driverMapper.toEntity(driver);
        DriverEntity savedEntity= jpaDriverRepository.save(entity);
        return driverMapper.toDomain(savedEntity);
    }
    public Optional<Driver> findById(DriverId id) {
        DriverEntity entity=jpaDriverRepository.findById(id.toString()).orElseThrow(()->new ResourceNotFoundException("Driver not found"));
        return Optional.ofNullable(driverMapper.toDomain(entity));
    }
}
