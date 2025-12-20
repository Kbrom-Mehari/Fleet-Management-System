package org.securityapps.vehicletracking.Netty.publisher;

import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;

public interface GpsMessagePublisher {
    void publish(GpsMessage gpsMessage);
}
