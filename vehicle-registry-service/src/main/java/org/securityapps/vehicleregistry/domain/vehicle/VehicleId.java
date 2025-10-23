package org.securityapps.vehicleregistry.domain.vehicle;

import java.util.UUID;

public record VehicleId(String id) {
    public VehicleId{
        if(id==null||id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be null or blank");
        }
    }
    public static VehicleId newId() {
        return new VehicleId(UUID.randomUUID().toString());
    }
    public static VehicleId of(String id){
        return new VehicleId(id);
    }
}
