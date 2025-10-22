package org.securityapps.vehicleregistry.adapter.outbound.persistence;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicleregistry.adapter.outbound.persistence.jpa.JpaVehicleOwnerRepository;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwner;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleOwnerRepositoryImpl implements VehicleOwnerRepository {
    private final JpaVehicleOwnerRepository jpaVehicleOwnerRepository;
    @Override
    public VehicleOwner save(VehicleOwner vehicleOwner) {
        return jpaVehicleOwnerRepository.save(vehicleOwner);
    }
    @Override
    public Optional<VehicleOwner> findById(VehicleOwnerId id){
        return jpaVehicleOwnerRepository.findById(id);
    }
}
