package com.ruisu.kafka.producer;


import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Component
@Profile("producer")
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaTemplate<String, String> template;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @PostConstruct
    public void send(){
        //This set listener for the all the messages sent through this Producer instance.
        //If isInterestedInSuccess returns false, listener is ignored.
        template.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
                log.info("Producer sent Successfully message with key: {} and value: {} for topic: {} and partition: {}", topic, partition, key, value);
            }

            @Override
            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
                log.info("Producer failed to send message with key: {} and value: {} for topic: {} and partition:{}", topic, partition, key, value);
            }

            @Override
            public boolean isInterestedInSuccess() {
                return true;
            }
        });

        //Send 100 messages
        IntStream.range(0,100).forEach(value -> template.send("bar", 0, "message-with-partition","Hello World " + value));

        ListenableFuture<SendResult<String, String>> futureFoo = template.send("bar", "message-without-partition","Future Message");

        //Add a callback to the specific ProducerRecord.

        futureFoo.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("Record Specific Future Failure. Error: {}", throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Record Specific Future Success: {}", result.getProducerRecord());
            }
        });



    }
}