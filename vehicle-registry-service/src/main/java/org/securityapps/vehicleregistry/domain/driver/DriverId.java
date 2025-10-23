package org.securityapps.vehicleregistry.domain.driver;

import java.util.Objects;
import java.util.UUID;

public record DriverId(String id) {
    public DriverId {
        if(id==null||id.isEmpty()){
            throw new IllegalArgumentException("id cannot be null or blank");
        }
    }
    public static DriverId newId(){
        return new DriverId(UUID.randomUUID().toString());
    }
    public static DriverId of(String id){
        return new DriverId(id);   // java created a constructor
    }

}
