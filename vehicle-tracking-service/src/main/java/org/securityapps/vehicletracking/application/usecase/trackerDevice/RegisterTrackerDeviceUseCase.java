package org.securityapps.vehicletracking.application.usecase.trackerDevice;

import org.securityapps.vehicletracking.application.dto.RegisterTrackerDeviceDto;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDevice;

public interface RegisterTrackerDeviceUseCase {
    TrackerDevice registerTrackerDevice(RegisterTrackerDeviceDto trackerDeviceDto);
}