package org.securityapps.vehicletracking.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tracker_device")
public class TrackerDeviceEntity {
    @Id
    @Column(name = "tracker_device_id")
    private String trackerDeviceId;
    private String serialNumber;
    private String imei;
    private String simNumber;
    private boolean isDamaged;
    private boolean isActive;
}
