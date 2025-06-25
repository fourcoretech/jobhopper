package com.interview.prep.service;

import com.resume.common.library.exception.NotificationException;
import com.resume.common.library.model.notify.dto.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;
    @Value("${spring.kafka.topic.name:notification.events}")
    private String topicName;

    public void sendNotificationEvent(NotificationEvent event) {
        try {
            log.info("Sending {} event for {} to {}.", event.getEventType(), event.getRecipientEmail(), topicName);
            kafkaTemplate.send(topicName, event).get();
        } catch (Exception e) {
            log.error("Failed to send Kafka event: {}", e.getMessage(), e);
            throw new NotificationException(String.format("Failed to send notification event for %s. %s", event.getRecipientEmail(), e.getMessage()), HttpStatusCode.valueOf(500));
        }
    }
}
