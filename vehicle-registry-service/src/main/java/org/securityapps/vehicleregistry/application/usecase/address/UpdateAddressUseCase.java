package org.securityapps.vehicleregistry.application.usecase.address;

import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.domain.address.AddressId;

public interface UpdateAddressUseCase {
    void updateAddress(UpdateAddressRequest dto,AddressId addressId);
}