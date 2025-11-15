package org.securityapps.vehicletracking.domain.trackerDevice;

import java.util.UUID;

public record TrackerDeviceId(String trackerDeviceId) {
    public static TrackerDeviceId newId(){return new TrackerDeviceId(UUID.randomUUID().toString());}
    public static TrackerDeviceId from(String trackerDeviceId){return new TrackerDeviceId(trackerDeviceId);}
}
