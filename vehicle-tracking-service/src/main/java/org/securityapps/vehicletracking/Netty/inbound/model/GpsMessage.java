package org.securityapps.vehicletracking.Netty.inbound.model;

import java.time.Instant;

public interface GpsMessage {
    void setImei(String imei);
    void setReceivedAt(Instant serverTime);
    String getImei();
}
