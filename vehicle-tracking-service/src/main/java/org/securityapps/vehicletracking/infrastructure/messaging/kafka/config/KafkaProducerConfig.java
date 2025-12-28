package org.securityapps.vehicletracking.infrastructure.messaging.kafka.config;

import org.securityapps.vehicletracking.domain.event.GpsRecordedEvent;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {
    @Bean
    ProducerFactory<String, GpsRecordedEvent> producerFactory(KafkaProperties kafkaProperties){
        //KafkaProperties builds all the kafka properties values from application.yml
        return new DefaultKafkaProducerFactory<>(
               kafkaProperties.buildProducerProperties()
        );
    }
    @Bean
    public KafkaTemplate<String, GpsRecordedEvent> kafkaTemplate (
            ProducerFactory<String,GpsRecordedEvent> producerFactory){ //injecting producer factory (DI)
        return new KafkaTemplate<>(producerFactory);
    }
}
