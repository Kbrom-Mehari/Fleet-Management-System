package org.securityapps.vehicletracking.application.service.trackerDevice;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.application.dto.RegisterTrackerDeviceDto;
import org.securityapps.vehicletracking.application.usecase.trackerDevice.RegisterTrackerDeviceUseCase;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDevice;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackerDeviceRegistrationService implements RegisterTrackerDeviceUseCase {
    private final TrackerDeviceRepository trackerDeviceRepository;

    @Override
    public TrackerDevice registerTrackerDevice(RegisterTrackerDeviceDto dto){
        TrackerDevice device=TrackerDevice.create(dto.serialNumber(),dto.imei(),dto.simNumber());
        trackerDeviceRepository.save(device);
        return device;
    }
}
