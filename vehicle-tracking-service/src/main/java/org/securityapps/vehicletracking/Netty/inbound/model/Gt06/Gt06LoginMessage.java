package org.securityapps.vehicletracking.Netty.inbound.model.Gt06;

import lombok.Getter;
import org.securityapps.vehicletracking.Netty.inbound.model.DeviceLoginMessage;

@Getter
public class Gt06LoginMessage implements DeviceLoginMessage {
    private String imei;
    private final String deviceType = "Gt06";
}
