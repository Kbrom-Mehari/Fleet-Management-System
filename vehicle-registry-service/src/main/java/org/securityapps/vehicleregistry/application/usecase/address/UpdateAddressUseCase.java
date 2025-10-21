package org.securityapps.vehicleregistry.application.usecase.address;

import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;

public interface UpdateAddressUseCase {
    void updateAddress(UpdateAddressRequest dto);
}
