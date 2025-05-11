package com.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
@AllArgsConstructor
public class AuthException extends RuntimeException {
    private final String errorMessage;
    private final HttpStatusCode statusCode;
}
