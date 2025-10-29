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
@RequiredArgsConstructor
public class DriverApplicationService implements RegisterDriverUseCase {
    private final DriverRepository driverRepository;
    private final UpdateAddressService  updateAddressService;

    public void updateAddress(DriverId driverId, UpdateAddressRequest updateAddressRequest) {
        Driver driver=driverRepository.findById(driverId).orElseThrow(()->new RuntimeException("Driver not found"));
        updateAddressService.updateAddress(updateAddressRequest,driver.getAddressId());
        driverRepository.save(driver);

    }
    @Override
    public RegisterDriverResponse registerDriver(RegisterDriverRequest request, AddressDTO addressDTO) {
        Address address = Address.create(AddressId.newId(),addressDTO.kebeleIdNumber(), addressDTO.faydaNumber(), addressDTO.region(), addressDTO.city(), addressDTO.woreda(), addressDTO.kebele(), addressDTO.phoneNumber());
        Driver driver= Driver.register(DriverId.newId(), request.firstName(), request.lastName(), request.licenseNumber(), address.getId());
        driverRepository.save(driver);
        return new RegisterDriverResponse(driver.getId(), driver.getFirstName(), driver.getLastName(), driver.getLicenseNumber(), driver.getAddressId());
    }
}
