package org.securityapps.vehicleregistry.domain.vehicleowner;

import java.util.UUID;

public record VehicleOwnerId(String id) {
    public VehicleOwnerId{
        if(id==null||id.isEmpty()) {
            throw new IllegalArgumentException("Vehicle owner id cannot be empty");
        }
    }
    public static VehicleOwnerId newId(){
        return new VehicleOwnerId(UUID.randomUUID().toString());
    }
    public static VehicleOwnerId of(String id) {
        return new VehicleOwnerId(id);
    }
}
