package org.securityapps.vehicletracking.domain.vehicleTrackingSession.dto;

import java.time.Duration;

public record SessionAnalytics(Duration sessionLasted,
                               double distanceCoveredInKilometers,
                               double averageSpeed,
                               double maxSpeed,
                               int speedLimitViolations) {
}
