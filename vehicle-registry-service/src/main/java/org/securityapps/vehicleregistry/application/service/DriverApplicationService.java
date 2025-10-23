package org.securityapps.vehicleregistry.application.service;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicleregistry.application.dto.AddressDTO;
import org.securityapps.vehicleregistry.application.dto.RegisterDriverRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterDriverResponse;
import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.application.usecase.address.UpdateAddressUseCase;
import org.securityapps.vehicleregistry.application.usecase.driver.RegisterDriverUseCase;
import org.securityapps.vehicleregistry.domain.address.Address;
import org.securityapps.vehicleregistry.domain.address.AddressId;
import org.securityapps.vehicleregistry.domain.driver.Driver;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.driver.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DriverApplicationService implements UpdateAddressUseCase, RegisterDriverUseCase {
//    private final DriverRepository driverRepository;
//    public DriverApplicationService(DriverRepository driverRepository) {
//        this.driverRepository = driverRepository;
//    }
    @Override
    public void updateAddress(UpdateAddressRequest request){

    }
    @Override
    public RegisterDriverResponse registerDriver(RegisterDriverRequest request, AddressDTO addressDTO) {
        Address address = Address.create(AddressId.newId(),addressDTO.kebeleIdNumber(), addressDTO.faydaNumber(), addressDTO.region(), addressDTO.city(), addressDTO.woreda(), addressDTO.kebele(), addressDTO.phoneNumber());
        Driver driver= Driver.register(DriverId.newId(), request.firstName(), request.lastName(), request.licenseNumber(), address.getId());

        return new RegisterDriverResponse(driver.getId(), driver.getFirstName(), driver.getLastName(), driver.getLicenseNumber(), driver.getAddressId());
    }
}
