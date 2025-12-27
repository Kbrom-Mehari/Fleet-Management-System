package org.securityapps.vehicletracking.Netty.publisher;

import org.securityapps.vehicletracking.infrastructure.messaging.kafka.model.GpsRecordedEvent;

public interface GpsMessagePublisher {
    void publish(GpsRecordedEvent gpsRecordedEvent);
}
