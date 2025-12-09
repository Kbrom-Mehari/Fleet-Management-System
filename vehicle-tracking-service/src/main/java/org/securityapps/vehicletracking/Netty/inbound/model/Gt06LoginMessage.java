package org.securityapps.vehicletracking.Netty.inbound.model;

import lombok.Getter;

@Getter
public class Gt06LoginMessage implements GpsLoginMessage {
    private String imei;
    private final String deviceType = "Gt06";
}
