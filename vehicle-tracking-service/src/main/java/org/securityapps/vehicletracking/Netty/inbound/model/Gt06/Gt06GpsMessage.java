package org.securityapps.vehicletracking.Netty.inbound.model.Gt06;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Gt06GpsMessage {
    String imei;
    double latitude;
    double longitude;
    int speedKph;
    int altitude;
    int angle;
    int satellites;
    Instant deviceTime;
    Instant receivedAt;

}
