package com.newsfeed_system_design.newsfeed_worker_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "post_updates", groupId = "newsfeed_consumer")
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaHandler
    public void consumer(String message) {
        logger.info("Message consumed: {}", message);
    }
}
