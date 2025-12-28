package org.securityapps.vehicletracking.Netty.publisher;

import org.securityapps.vehicletracking.domain.event.GpsRecordedEvent;

public interface GpsMessagePublisher {
    void publish(GpsRecordedEvent gpsRecordedEvent);
}
