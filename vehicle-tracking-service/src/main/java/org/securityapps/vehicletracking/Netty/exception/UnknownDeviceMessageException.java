package org.securityapps.vehicletracking.Netty.exception;

public class UnknownDeviceMessageException extends RuntimeException{
    public UnknownDeviceMessageException(String message){
        super(message);
    }
}
