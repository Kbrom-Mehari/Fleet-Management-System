package org.securityapps.vehicletracking.domain.trackerDevice;

import java.util.List;
import java.util.Optional;

public interface TrackerDeviceRepository {
    void save(TrackerDevice trackerDevice);
    Optional<TrackerDevice> findById(TrackerDeviceId id);
    Optional<TrackerDevice> findByImei(String imei);
    List<TrackerDevice> findInactive();
}
