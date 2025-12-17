package org.securityapps.vehicletracking.Netty.inbound.model.teltonika;

import org.securityapps.vehicletracking.Netty.inbound.model.DeviceLoginMessage;

public record TeltonikaLoginMessage(String imei) implements DeviceLoginMessage {
    private static final String DEVICE_TYPE = "TELTONIKA";

    public String getImei(){
        return imei;
    }
    public String getDeviceType(){
        return DEVICE_TYPE;
    }
}
