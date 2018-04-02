package com.ruisu.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;


@Component
@Profile("consumer")
public class KafkaConsumer {

    private Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "bar")
    public void listen(ConsumerRecord<String, String> consumerRecord) {
        log.info("CONSUMING MESSAGE");
        log.info("{}", consumerRecord.toString());
    }

    @KafkaListener(
            topicPartitions = {@TopicPartition(topic = "bar", partitions = {"0"})},
            groupId = "partitioned-group"
    )
    public void listenToPartition(ConsumerRecord<String, String> consumerRecord) {
        log.info("CONSUMING MESSAGE FROM PARTITION 0");
        log.info("{}", consumerRecord.toString());
    }

}

