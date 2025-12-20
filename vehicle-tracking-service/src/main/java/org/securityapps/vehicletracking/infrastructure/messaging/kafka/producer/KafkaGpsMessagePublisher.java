package org.securityapps.vehicletracking.infrastructure.messaging.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.securityapps.vehicletracking.Netty.inbound.model.GpsMessage;
import org.securityapps.vehicletracking.Netty.publisher.GpsMessagePublisher;


@RequiredArgsConstructor
public class KafkaGpsMessagePublisher implements GpsMessagePublisher {
    private final KafkaProducer<String, GpsMessage> producer;

    }
}
