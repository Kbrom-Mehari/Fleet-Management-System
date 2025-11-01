package org.securityapps.vehicleregistry.outbound.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name="vehicle")
public class VehicleEntity {
    @Id
    @Column(name="vehicle_id")
    private String vehicleId;
    @Column(name = "vehicle_owner_id")
    private String vehicleOwnerId;
    @Column(name = "assigned_driver_id")
    private String assignedDriverId;
    @Column(name = "model_name")
    private String modelName;
    @Column(name = "plate_number")
    private String plateNumber;
    private String vin;


    public VehicleEntity(String vehicleId, String vehicleOwnerId, String assignedDriverId, String modelName, String plateNumber, String vin) {
        this.vehicleId = vehicleId;
        this.vehicleOwnerId = vehicleOwnerId;
        this.assignedDriverId = assignedDriverId;
        this.modelName = modelName;
        this.plateNumber = plateNumber;
        this.vin = vin;
    }
    public VehicleEntity() {}
}
