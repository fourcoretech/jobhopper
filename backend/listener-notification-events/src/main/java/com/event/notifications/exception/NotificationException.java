package com.event.notifications.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
@AllArgsConstructor
public class NotificationException extends RuntimeException {
    private final String errorMessage;
    private final HttpStatusCode statusCode;
}
