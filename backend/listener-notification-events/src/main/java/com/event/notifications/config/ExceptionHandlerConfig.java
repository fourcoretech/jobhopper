package com.event.notifications.config;

import com.event.notifications.exception.NotificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
@Slf4j
public class ExceptionHandlerConfig {
    @ExceptionHandler({NotificationException.class})
    public static ResponseEntity<String> notificationException(NotificationException notificationException) {
        log.error(notificationException.getErrorMessage());
        return ResponseEntity
                .status(notificationException.getStatusCode())
                .body(notificationException.getErrorMessage());
    }
}
