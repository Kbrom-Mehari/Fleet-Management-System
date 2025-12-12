package org.securityapps.vehicletracking.Netty.inbound.decoder.teltonica;

import lombok.Getter;

public class TeltonicaImeiFrame {
    private final String imei;

    public TeltonicaImeiFrame(String imei){
        this.imei = imei;
    }
    public String getImei(){
        return imei;
    }
    @Override
    public String toString() {
        return "TeltonikaImeiFrame{imei='" + imei + "'}";
    }
}
