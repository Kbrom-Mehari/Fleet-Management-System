package org.securityapps.vehicletracking.common.dto;

import java.time.Instant;
import java.util.Optional;

public interface NormalizedGpsMessage {
    String imei();
    double latitude();
    double longitude();
    double speedKph();
    int satellites();
    int angle();
    Instant deviceTime();
    Instant receivedAt();
    Optional<Double> fuelLevel();
    Optional<Boolean> ignitionOn();
    Optional<Boolean> crashDetected();
}
