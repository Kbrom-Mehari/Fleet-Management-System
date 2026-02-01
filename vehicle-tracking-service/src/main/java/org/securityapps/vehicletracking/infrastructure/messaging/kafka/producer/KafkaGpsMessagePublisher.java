package org.securityapps.vehicletracking.infrastructure.messaging.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.securityapps.vehicletracking.Netty.publisher.GpsMessagePublisher;
import org.securityapps.vehicletracking.domain.event.GpsRecordedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

@Slf4j
@RequiredArgsConstructor
public class KafkaGpsMessagePublisher implements GpsMessagePublisher {
    private final KafkaTemplate<String, GpsRecordedEvent> kafkaTemplate;
    private static final String TOPIC = "gps.recorded.v1";

    @Override
    public void publish(GpsRecordedEvent event){
        String key = event.imei();
        kafkaTemplate
                .send(TOPIC,key,event)
                .whenComplete(
                        (result, ex)->{
                            if(ex != null){
                                handleFailure(event,ex);
                            }
                            else
                                handleSuccess(event,result);
                        }
                );

    }
    private void handleSuccess(GpsRecordedEvent event,
                               SendResult<String, GpsRecordedEvent> result){
        RecordMetadata metadata = result.getRecordMetadata();
        log.debug(
                "Gps event published | topic={}, partition={} offset={} imei={}",
                metadata.topic(),
                metadata.partition(),
                metadata.offset(),
                event.imei()
        );

    }
    private void handleFailure(GpsRecordedEvent event, Throwable ex){
        log.error(
                "Failed to publish Gps Event: imei={}",
                event.imei(),
                ex
        );

    }
}
