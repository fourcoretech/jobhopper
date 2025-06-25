package com.event.notifications.consumer;


import com.event.notifications.exception.NotificationException;
import com.event.notifications.service.NotificationService;
import com.resume.common.library.model.notify.dto.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService notificationService;

    @KafkaListener(
            topics = "notification.events",
            groupId = "notification-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(NotificationEvent event) {
        try {
            log.info("Received new {} event.", event.getEventType());
            notificationService.sendEmail(event);
        } catch (Exception e) {
            log.error("Failed to process event", e);
            throw new NotificationException(
                    String.format("Failed to process event. %s", e.getMessage()),
                    HttpStatusCode.valueOf(500)
            );
        }
    }
}
