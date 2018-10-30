package com.ruisu.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("multi-consumer")
public class KafkaMultiConsumer {

    private Logger log = LoggerFactory.getLogger(KafkaMultiConsumer.class);

    @KafkaListener(topics = "foo", groupId = "foo-group")
    public void listenFoo(ConsumerRecord<String, String> consumerRecord) {
        log.info("CONSUMING MESSAGE FROM PARTITION {}: {}", consumerRecord.partition(), consumerRecord.value());
    }
}
