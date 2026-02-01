package org.securityapps.vehicletracking.common.util;

import org.securityapps.vehicletracking.common.dto.NormalizedGpsMessage;
import org.securityapps.vehicletracking.domain.event.GpsRecordedEvent;

public class GpsMessageToEventMapper {
    public static GpsRecordedEvent map(NormalizedGpsMessage message){
        return new GpsRecordedEvent(message.imei(),
                message.latitude(),
                message.longitude(),
                message.latitude(),
                message.speedKph(),
                message.satellites(),
                message.angle(),
                message.);
    }
}
