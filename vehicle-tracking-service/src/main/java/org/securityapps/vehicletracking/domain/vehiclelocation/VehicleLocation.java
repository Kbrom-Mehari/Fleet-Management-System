package org.securityapps.vehicletracking.domain.vehiclelocation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class VehicleLocation {
    @Id
    private Long id;
    private String vehicleId;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private LocalDateTime timestamp;
    private Double speed;
    private Double course;

}
