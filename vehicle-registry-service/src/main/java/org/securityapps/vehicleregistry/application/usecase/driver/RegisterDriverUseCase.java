package org.securityapps.vehicleregistry.application.usecase.driver;

import org.securityapps.vehicleregistry.application.dto.RegisterDriverRequest;
import org.securityapps.vehicleregistry.application.dto.RegisterDriverResponse;

public interface RegisterDriverUseCase {
  RegisterDriverResponse registerDriver(RegisterDriverRequest registerDriverRequest);
}
