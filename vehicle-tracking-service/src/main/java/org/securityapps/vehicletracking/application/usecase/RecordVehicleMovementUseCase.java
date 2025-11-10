package org.securityapps.vehicletracking.application.usecase;

import org.securityapps.vehicletracking.application.dto.RecordVehicleMovementRequest;
import org.securityapps.vehicletracking.application.dto.RecordVehicleMovementResponse;

public interface RecordVehicleMovementUseCase {
    RecordVehicleMovementResponse recordMovement(RecordVehicleMovementRequest request);
}
