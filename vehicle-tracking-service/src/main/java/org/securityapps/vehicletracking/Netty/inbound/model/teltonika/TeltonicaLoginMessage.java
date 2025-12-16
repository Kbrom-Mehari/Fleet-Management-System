package org.securityapps.vehicletracking.Netty.inbound.model.teltonika;

import org.securityapps.vehicletracking.Netty.inbound.model.DeviceLoginMessage;

public record TeltonicaLoginMessage(String imei) implements DeviceLoginMessage {
    private static final String DEVICE_TYPE = "Teltonika";

    public String getImei(){
        return imei;
    }
    public String getDeviceType(){
        return DEVICE_TYPE;
    }
}
