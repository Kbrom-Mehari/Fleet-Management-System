package org.securityapps.vehicletracking.domain.vehicleTrackingSession.dto;

public record SessionAnalytics(double distanceCovered,
                               int speedLimitViolations,
                               int crashReports) {
}
