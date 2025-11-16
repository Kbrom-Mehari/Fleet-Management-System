package org.securityapps.vehicletracking.infrastructure.messaging.redis;

import lombok.extern.slf4j.Slf4j;

import org.securityapps.vehicletracking.application.service.VehicleTrackingSessionManager;
import org.securityapps.vehicletracking.domain.trackerDevice.TrackerDeviceId;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisSessionExpirationListener extends KeyExpirationEventMessageListener {
    private final VehicleTrackingSessionManager sessionManager;

    public RedisSessionExpirationListener(RedisMessageListenerContainer listenerContainer,
                                          VehicleTrackingSessionManager  sessionManager) {
        super(listenerContainer); //super class needs it
        this.sessionManager = sessionManager;
    }
    @Override
    public void onMessage(Message message, byte[] pattern){
        String expiredKey=message.toString();  // redis sends the expired key as a message
        log.info("Redis Key expired: {}", expiredKey);

        //check if this is session key (what I need to end sessions)
        if(expiredKey.startsWith("tracking_session:")){
           String deviceId= expiredKey.replace("tracking_session:","");
           try{
               log.info("Ending session fo device {}",deviceId);
               sessionManager.endSession(TrackerDeviceId.from(deviceId));
           }
           catch(Exception e){
               log.error("Failed to end session for expired key {}: {}",expiredKey,e.getMessage());
           }
        }
    }
}
