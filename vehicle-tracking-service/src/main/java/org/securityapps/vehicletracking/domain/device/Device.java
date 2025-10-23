package org.securityapps.vehicletracking.domain.device;

import jakarta.persistence.*;
import org.securityapps.vehicletracking.domain.enums.DeviceStatus;

@Entity
public class Device  {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String imei;
private String model;
private String simNumber;
private String firmwareVersion;
private DeviceStatus status;

}
