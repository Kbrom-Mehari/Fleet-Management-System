package org.securityapps.vehicletracking.Netty.inbound.model.Gt06;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.common.dto.NormalizedGpsMessage;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class Gt06GpsAdapter implements NormalizedGpsMessage {
    private final Gt06GpsMessage message;

    @Override
    public String imei(){
        return message.imei;
    }
    @Override
    public double latitude(){
        return message.latitude;
    }
    @Override
    public double longitude(){
        return message.longitude;
    }
    @Override
    public double speedKph(){
        return message.speedKph;
    }
    @Override
    public int satellites(){return message.satellites;}
    @Override
    public int angle(){return message.angle;}
    @Override
    public Instant deviceTime(){return message.deviceTime;}
    @Override
    public Instant receivedAt(){
        return message.receivedAt;
    }
    @Override
    public Optional<Double> fuelLevel(){
        return Optional.empty();
    }
    @Override
    public Optional<Boolean> ignitionOn(){
        return Optional.empty();
    }
    @Override
    public Optional<Boolean> crashDetected(){
        return Optional.empty();
    }
}
