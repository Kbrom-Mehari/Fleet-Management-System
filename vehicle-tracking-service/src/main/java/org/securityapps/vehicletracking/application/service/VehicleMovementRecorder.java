package org.securityapps.vehicletracking.application.service;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.application.dto.RecordVehicleMovementRequest;
import org.securityapps.vehicletracking.application.dto.RecordVehicleMovementResponse;
import org.securityapps.vehicletracking.application.usecase.RecordVehicleMovementUseCase;
import org.securityapps.vehicletracking.domain.vehicleTrackingSession.repository.VehicleTrackingSessionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleMovementRecorder implements RecordVehicleMovementUseCase {
    private final VehicleTrackingSessionRepository vehicleTrackingSessionRepository;

    public RecordVehicleMovementResponse recordMovement(RecordVehicleMovementRequest request){

    }
}
