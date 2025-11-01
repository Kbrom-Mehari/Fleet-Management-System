package org.securityapps.vehicleregistry.outbound.persistence.adapter;

import org.securityapps.vehicleregistry.common.exception.ResourceNotFoundException;
import org.securityapps.vehicleregistry.outbound.persistence.entity.VehicleOwnerEntity;
import org.securityapps.vehicleregistry.outbound.persistence.mapper.VehicleOwnerMapper;
import org.securityapps.vehicleregistry.outbound.persistence.repository.JpaVehicleOwnerRepository;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwner;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public class VehicleOwnerRepositoryImpl implements VehicleOwnerRepository {
    private final JpaVehicleOwnerRepository jpaVehicleOwnerRepository;
    private final VehicleOwnerMapper vehicleOwnerMapper;

    public VehicleOwnerRepositoryImpl(JpaVehicleOwnerRepository jpaVehicleOwnerRepository,VehicleOwnerMapper vehicleOwnerMapper) {
        this.jpaVehicleOwnerRepository = jpaVehicleOwnerRepository;
        this.vehicleOwnerMapper = vehicleOwnerMapper;
    }

    @Override
    public VehicleOwner save(VehicleOwner vehicleOwner) {
        VehicleOwnerEntity entity = vehicleOwnerMapper.toEntity(vehicleOwner);
        VehicleOwnerEntity savedEntity= jpaVehicleOwnerRepository.save(entity);
        return vehicleOwnerMapper.toDomain(savedEntity);
    }
    @Override
    public Optional<VehicleOwner> findById(VehicleOwnerId id){
        VehicleOwnerEntity entity=jpaVehicleOwnerRepository.findById(id.toString()).orElseThrow(()->new ResourceNotFoundException("Vehicle owner not found"));
        return Optional.ofNullable(vehicleOwnerMapper.toDomain(entity));
    }
}
