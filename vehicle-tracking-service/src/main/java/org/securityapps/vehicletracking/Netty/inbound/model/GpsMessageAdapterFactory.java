package org.securityapps.vehicletracking.Netty.inbound.model;

import org.securityapps.vehicletracking.Netty.exception.UnknownDeviceMessageException;
import org.securityapps.vehicletracking.Netty.inbound.model.Gt06.Gt06GpsAdapter;
import org.securityapps.vehicletracking.Netty.inbound.model.Gt06.Gt06GpsMessage;
import org.securityapps.vehicletracking.Netty.inbound.model.teltonika.TeltonikaGpsAdapter;
import org.securityapps.vehicletracking.Netty.inbound.model.teltonika.TeltonikaGpsMessage;
import org.securityapps.vehicletracking.common.dto.NormalizedGpsMessage;

public class GpsMessageAdapterFactory {
    public static NormalizedGpsMessage adapt(Object gpsMessage){
        if(gpsMessage instanceof TeltonikaGpsMessage tm){
            return new TeltonikaGpsAdapter(tm);
        }
        if(gpsMessage instanceof Gt06GpsMessage gm){
            return new Gt06GpsAdapter(gm);
        }
        throw new UnknownDeviceMessageException("Unsupported device message: "+gpsMessage.getClass().getSimpleName());
    }
}
