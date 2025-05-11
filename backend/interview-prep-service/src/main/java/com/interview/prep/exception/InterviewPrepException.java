package com.interview.prep.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
@AllArgsConstructor
public class InterviewPrepException extends RuntimeException {
    private final HttpStatusCode statusCode;
    private final String errorMessage;

    public InterviewPrepException(String errorMessage, HttpStatusCode statusCode) {
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }
}
