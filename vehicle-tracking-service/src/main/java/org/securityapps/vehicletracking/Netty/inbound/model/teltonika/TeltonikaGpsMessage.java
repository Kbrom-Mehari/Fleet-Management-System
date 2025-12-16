package org.securityapps.vehicletracking.Netty.inbound.model.teltonika;

import lombok.Getter;
import lombok.Setter;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class TeltonikaGpsMessage extends GpsMessage {
    double latitude;
    double longitude;
    int speedKph;
    int altitude;
    int angle;
    int satellites;
    int priority;
    int eventId;
    int totalIO;
    Map<Integer,Object> ioElements;
    Instant deviceTime;

}
