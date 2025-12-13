package org.securityapps.vehicletracking.Netty.inbound.model;

public record TeltonicaLoginMessage(String imei) implements GpsLoginMessage {
    private static final String DEVICE_TYPE = "Teltonika";

    public String getImei(){
        return imei;
    }
    public String getDeviceType(){
        return DEVICE_TYPE;
    }
}
