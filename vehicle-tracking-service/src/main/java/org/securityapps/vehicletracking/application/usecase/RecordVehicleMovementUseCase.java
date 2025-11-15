package org.securityapps.vehicletracking.application.usecase;

import org.securityapps.vehicletracking.common.dto.VehicleLocationPoint;

public interface RecordVehicleMovementUseCase {
    void recordMovement(VehicleLocationPoint locationPoint);
}
