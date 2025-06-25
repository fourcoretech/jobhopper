package com.resume.common.library.client;

import com.resume.common.library.exception.OpenAIException;
import com.resume.common.library.model.openai.dto.ChatCompletionDTO;
import com.resume.common.library.model.openai.dto.GPTPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenAIClient {

    private final WebClient openAiWebClient;

    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public ChatCompletionDTO prompt(GPTPrompt prompt) {

        try {
            return openAiWebClient.post()
                    .uri("/chat/completions")
                    .bodyValue(prompt)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response ->
                            response.bodyToMono(String.class)
                                    .flatMap(body -> Mono.error(new OpenAIException(
                                            String.format("OpenAI error (%d): %s", response.statusCode().value(), body),
                                            response.statusCode()
                                    )))
                    )
                    .bodyToMono(ChatCompletionDTO.class)
                    .block();
        } catch (Exception ex) {
            log.error("OpenAI call failed: {}", ex.getMessage(), ex);
            throw (ex instanceof OpenAIException)
                    ? (OpenAIException) ex
                    : new OpenAIException("OpenAI request failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
