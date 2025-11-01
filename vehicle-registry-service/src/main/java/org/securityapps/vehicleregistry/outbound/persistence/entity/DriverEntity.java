package org.securityapps.vehicleregistry.outbound.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.securityapps.vehicleregistry.common.valueObject.Address;

@Entity
@Table(name="driver")
@Getter
public class DriverEntity {
    @Id
    @Column(name = "driver_id")
    private String driverId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "licence_number")
    private String licenseNumber;
    @Embedded
    private Address address;

    public DriverEntity(String driverId, String firstName, String lastName, String licenseNumber, Address address) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.address = address;
    }
    public DriverEntity() {}

}
