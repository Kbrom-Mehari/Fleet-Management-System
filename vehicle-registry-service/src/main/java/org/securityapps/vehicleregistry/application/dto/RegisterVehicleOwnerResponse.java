package org.securityapps.vehicleregistry.application.dto;

import org.securityapps.vehicleregistry.domain.address.AddressId;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

public record RegisterVehicleOwnerResponse(
        VehicleOwnerId id,
        String firstName,
        String lastName,
        AddressId addressId) { }
