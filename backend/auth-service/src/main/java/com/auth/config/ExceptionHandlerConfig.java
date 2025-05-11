package com.auth.config;


import com.auth.controller.AuthController;
import com.auth.exception.AuthException;
import com.resume.common.library.exception.JWTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice(assignableTypes = {AuthController.class})
@Slf4j
public class ExceptionHandlerConfig {

    @ExceptionHandler({JWTException.class})
    public static ResponseEntity<String> jwtException(JWTException jwtException) {
        log.error(jwtException.getErrorMessage());
        return ResponseEntity
                .status(jwtException.getStatusCode())
                .body(jwtException.getErrorMessage());
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public static ResponseEntity<String> httpClientErrorException(HttpClientErrorException httpClientErrorException) {
        log.error(httpClientErrorException.getMessage());
        return ResponseEntity
                .status(httpClientErrorException.getStatusCode())
                .body(httpClientErrorException.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public static ResponseEntity<String> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        log.error(httpRequestMethodNotSupportedException.getMessage());
        return ResponseEntity
                .status(httpRequestMethodNotSupportedException.getStatusCode())
                .body(httpRequestMethodNotSupportedException.getMessage());
    }


    @ExceptionHandler({BadCredentialsException.class})
    public static ResponseEntity<String> badCredentialsException(BadCredentialsException badCredentialsException) {
        log.error(badCredentialsException.getMessage());
        return ResponseEntity
                .status(400)
                .body(badCredentialsException.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public static ResponseEntity<String> usernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
        log.error(usernameNotFoundException.getMessage());
        return ResponseEntity
                .status(404)
                .body(usernameNotFoundException.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public static ResponseEntity<String> illegalArgumentException(IllegalArgumentException illegalArgumentException) {
        log.error(illegalArgumentException.getMessage());
        return ResponseEntity
                .status(400)
                .body(illegalArgumentException.getMessage());
    }

    @ExceptionHandler({IllegalStateException.class})
    public static ResponseEntity<String> illegalStateException(IllegalStateException illegalStateException) {
        log.error(illegalStateException.getMessage());
        return ResponseEntity
                .status(400)
                .body(illegalStateException.getMessage());
    }

    @ExceptionHandler({AuthException.class})
    public static ResponseEntity<String> authException(AuthException authException) {
        log.error(authException.getErrorMessage());
        return ResponseEntity
                .status(authException.getStatusCode())
                .body(authException.getErrorMessage());
    }
}
