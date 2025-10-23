package org.securityapps.vehicleregistry.domain.vehicle;

import lombok.Getter;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.event.DriverAssignedEvent;
import org.securityapps.vehicleregistry.domain.vehicle.event.VehicleRegisteredEvent;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Vehicle {
    private final VehicleId id;
    public DriverId assignedDriverId;
    public VehicleOwnerId vehicleOwnerId;
    private final String modelName;
    private final String plateNumber;
    private final String vin; //vehicle identification number (libre number)

    private Vehicle(VehicleId id, String modelName, String plateNumber, String vin) {
        this.id = id;
        this.modelName = modelName;
        this.plateNumber = plateNumber;
        this.vin = vin;
    }
    public static Vehicle create(VehicleId id, String modelName, String plateNumber, String vin) {
        return new Vehicle(id, modelName, plateNumber, vin);
    }
    private final List<Object> domainEvents = new ArrayList<>();

    public void assignDriver(DriverId driverId) {
        domainEvents.add(new DriverAssignedEvent(driverId,id, Instant.now()));
    }
    public void registerVehicle(VehicleOwnerId ownerId, VehicleId vehicleId) {
        domainEvents.add(new VehicleRegisteredEvent(vehicleId, ownerId, Instant.now()));
    }

    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
    public void clearDomainEvents() {
        domainEvents.clear();
    }

    @Override
    public boolean equals(Object object){
        if(this==object) return true;
        if(!(object instanceof Vehicle vehicle)) return false;
        return Objects.equals(id,vehicle.id);
    }
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

}
