package org.securityapps.vehicletracking.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle_tracking_session")
public class VehicleTrackingSessionEntity {
    @Id
    private String sessionId;
    private String trackerDeviceId;
    private double totalDistanceCovered;
    private Instant lastUpdate;
    private Instant startedAt;
    private Instant endedAt;
}
