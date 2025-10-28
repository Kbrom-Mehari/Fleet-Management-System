package org.securityapps.vehicleregistry.domain.vehicle;

import lombok.Getter;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.vehicle.event.DriverAssignedEvent;
import org.securityapps.vehicleregistry.domain.vehicle.event.DriverReassignedEvent;
import org.securityapps.vehicleregistry.domain.vehicle.event.VehicleRegisteredEvent;
import org.securityapps.vehicleregistry.domain.vehicleowner.VehicleOwnerId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Vehicle {
    private final VehicleId vehicleId;
    private DriverId assignedDriverId;
    private VehicleOwnerId vehicleOwnerId;
    private final String modelName;
    private final String plateNumber;
    private final String vin; //vehicle identification number (libre number)

    private final List<Object> domainEvents = new ArrayList<>();

    private Vehicle(VehicleId vehicleId, String modelName, String plateNumber, String vin) {
        this.vehicleId= vehicleId;
        this.modelName = modelName;
        this.plateNumber = plateNumber;
        this.vin = vin;
    }
    public static Vehicle register(VehicleId id, String modelName, String plateNumber, String vin) {
        Vehicle vehicle=new Vehicle(id, modelName, plateNumber, vin);
        vehicle.registerEvent();
        return vehicle;
    }

    public void registerEvent(){
        domainEvents.add(new VehicleRegisteredEvent(vehicleId,Instant.now()));
    }

    public void assignDriver(DriverId driverId) {
        if(this.assignedDriverId != null) {
            throw new IllegalStateException("Driver already assigned");
        }
        assignedDriverId=driverId;
        domainEvents.add(new DriverAssignedEvent(driverId,vehicleId, Instant.now()));
    }
    public void unAssignDriver(){
        this.assignedDriverId=null;
    }
    public void reAssignDriver(DriverId newDriverId){
        this.assignedDriverId=newDriverId;
        domainEvents.add(new DriverReassignedEvent(vehicleId,assignedDriverId,newDriverId,Instant.now()));
    }
    public void changeVehicleOwner(VehicleOwnerId newOwnerId){
        this.vehicleOwnerId=newOwnerId;
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
        return Objects.equals(vehicleId,vehicle.vehicleId);
    }
    @Override
    public int hashCode(){
        return Objects.hash(vehicleId);
    }

}
