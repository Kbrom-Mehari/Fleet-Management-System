package org.securityapps.vehicletracking.Netty.inbound.model.teltonika;

import lombok.RequiredArgsConstructor;
import org.securityapps.vehicletracking.common.dto.NormalizedGpsMessage;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class TeltonikaGpsAdapter implements NormalizedGpsMessage {
    private final TeltonikaGpsMessage message;

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
    public Instant recordedAt(){
        return message.deviceTime;
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
