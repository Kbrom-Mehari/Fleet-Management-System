package org.securityapps.vehicleregistry.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.securityapps.vehicleregistry.common.valueObject.Address;

@Entity
@Table(name="vehicle_owner")
public class VehicleOwnerEntity {
    @Id
    @Column(name = "vehicle_owner_id")
    private String vehicleOwnerId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Embedded
    private Address address;

    public VehicleOwnerEntity(String vehicleOwnerId, String firstName, String lastName, Address address) {
        this.vehicleOwnerId = vehicleOwnerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
    public VehicleOwnerEntity() {}

    public String getVehicleOwnerId() {return vehicleOwnerId;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public Address getAddress() {return address;}

}
