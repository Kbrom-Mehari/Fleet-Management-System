package org.securityapps.vehicletracking.domain.vehicleTrackingSession;

import java.util.Objects;
import java.util.UUID;

public record VehicleTrackingSessionId(String sessionId) {
   public VehicleTrackingSessionId {
       Objects.requireNonNull(sessionId, "sessionId can not be null");
   }
   public static VehicleTrackingSessionId newId(){
       return new VehicleTrackingSessionId(UUID.randomUUID().toString());
   }
   public static VehicleTrackingSessionId from(String sessionId){
       return new VehicleTrackingSessionId(sessionId);
   }
}
