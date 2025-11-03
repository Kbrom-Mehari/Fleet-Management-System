package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.common.valueObject.Address;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

public record RegisterVehicleOwnerResponse(
        VehicleOwnerId id,
        String firstName,
        String lastName,
        Address address) { }
