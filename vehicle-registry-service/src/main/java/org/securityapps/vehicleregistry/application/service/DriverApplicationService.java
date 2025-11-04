package org.securityapps.vehicleregistry.application.service;

import org.securityapps.vehicleregistry.application.dto.RegisterDriverRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterDriverResponse;
import org.securityapps.vehicleregistry.application.dto.UpdateAddressRequest;
import org.securityapps.vehicleregistry.application.usecase.driver.FindDriverUseCase;
import org.securityapps.vehicleregistry.application.usecase.driver.RegisterDriverUseCase;
import org.securityapps.vehicleregistry.application.usecase.driver.UpdateDriverAddressUseCase;
import org.securityapps.vehicleregistry.common.exception.ResourceNotFoundException;
import org.securityapps.vehicleregistry.common.valueObject.Address;
import org.securityapps.vehicleregistry.domain.driver.Driver;
import org.securityapps.vehicleregistry.domain.driver.DriverId;
import org.securityapps.vehicleregistry.domain.driver.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

public class DriverApplicationService implements RegisterDriverUseCase,
        UpdateDriverAddressUseCase,
        FindDriverUseCase {
    private final DriverRepository driverRepository;

    public DriverApplicationService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public void updateAddress(DriverId driverId, UpdateAddressRequest request) {
        Driver driver=driverRepository.findById(driverId).orElseThrow(()->new ResourceNotFoundException("Driver not found"));
        driver.updateAddress(request.region(),request.city(),request.woreda(),request.kebele(),request.phoneNumber());
        driverRepository.save(driver);
    }

    @Override
    public RegisterDriverResponse registerDriver(RegisterDriverRequest request) {
        Address address=new Address(request.address().getKebeleIdNumber(), request.address().getFaydaNumber(), request.address().getRegion(), request.address().getCity(), request.address().getWoreda(), request.address().getKebele(), request.address().getPhoneNumber());
        Driver driver=Driver.create(DriverId.newId(),request.firstName(),request.lastName(), request.licenseNumber(), address);
        driverRepository.save(driver);
        return new RegisterDriverResponse(driver.getDriverId(), driver.getFirstName(), driver.getLastName(),driver.getLicenseNumber(),driver.getAddress());
    }
    @Override
    public Driver findDriverById(DriverId driverId) {
        return driverRepository.findById(driverId).orElseThrow(()->new ResourceNotFoundException("Driver not found"));
    }
    @Override
    public Driver findDriverByPhoneNumber(String phoneNumber) {
        return driverRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new ResourceNotFoundException("Driver not found"));
    }
}
