package org.securityapps.vehicleregistry.infrastructure.persistence.mapper;

import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwner;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.securityapps.vehicleregistry.infrastructure.persistence.entity.VehicleOwnerEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleOwnerMapper {
    public VehicleOwner toDomain(VehicleOwnerEntity vehicleOwnerEntity) {
        return VehicleOwner.create(VehicleOwnerId.of(vehicleOwnerEntity.getVehicleOwnerId()),vehicleOwnerEntity.getFirstName(),vehicleOwnerEntity.getLastName(),vehicleOwnerEntity.getAddress());
    }
    public VehicleOwnerEntity toEntity(VehicleOwner vehicleOwner) {
        return new VehicleOwnerEntity(vehicleOwner.getVehicleOwnerId().toString(),vehicleOwner.getFirstName(),vehicleOwner.getLastName(),vehicleOwner.getAddress());
    }
}
