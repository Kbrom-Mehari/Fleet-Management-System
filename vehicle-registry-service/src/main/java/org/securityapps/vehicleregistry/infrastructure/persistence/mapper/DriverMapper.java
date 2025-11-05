package org.securityapps.vehicleregistry.infrastructure.persistence.mapper;

import org.securityapps.vehicleregistry.domain.driver.Driver;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.infrastructure.persistence.entity.DriverEntity;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {
    public Driver toDomain(DriverEntity driverEntity) {
        return Driver.create(DriverId.of(driverEntity.getDriverId()) , driverEntity.getFirstName(), driverEntity.getLastName(), driverEntity.getLicenseNumber(), driverEntity.getAddress());
    }
    public DriverEntity toEntity(Driver driver) {
        return new DriverEntity(driver.getDriverId().toString(), driver.getFirstName(), driver.getLastName(), driver.getLicenseNumber(), driver.getAddress());
    }
}
