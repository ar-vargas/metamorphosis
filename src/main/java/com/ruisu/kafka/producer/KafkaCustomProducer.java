package com.ruisu.kafka.producer;

import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("custom-producer")
public class KafkaCustomProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaCustomProducer.class);
    private final String topic = "foo";

    private KafkaTemplate<String, String> template;

    public KafkaCustomProducer(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @PostConstruct
    public void sendMessages() {
        sendCustomPartition();
    }

    private void sendCustomPartition() {
        log.info("Sending messages...");
        //Send 100 messages
        IntStream.range(0, 100).forEach(value ->
                template.send(topic, value % 3, "fixed-partition", "Hello " + value));

        IntStream.range(0, 100).forEach(value ->
                template.send(topic, String.valueOf(value), "World " + value));

    }
}
