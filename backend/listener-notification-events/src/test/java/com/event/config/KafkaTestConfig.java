package com.event.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.kafka.test.context.EmbeddedKafka;

@TestConfiguration
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaTestConfig {
    // This class is used to configure an embedded Kafka broker for testing purposes.
    // It sets up a single partition and specifies the broker properties.
    // No additional methods or fields are required for this configuration.
}
