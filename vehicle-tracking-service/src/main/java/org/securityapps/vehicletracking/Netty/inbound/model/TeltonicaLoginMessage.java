package org.securityapps.vehicletracking.Netty.inbound.model;

import lombok.Getter;

@Getter
public class TeltonicaLoginMessage implements GpsLoginMessage {
    private String imei;
    private final String deviceType = "Teltonica";
}
