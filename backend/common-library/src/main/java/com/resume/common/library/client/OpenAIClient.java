package com.resume.common.library.client;

import com.resume.common.library.dto.ChatCompletionDTO;
import com.resume.common.library.dto.GPTPrompt;
import com.resume.common.library.exception.OpenAIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
    @Autowired
    private final WebClient webClient;

    @Value("${ws.endpoint.open-ai-api}")
    private String baseURL;

    @Value("${openai.api-key}")
    private String apiKey;

    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public ChatCompletionDTO prompt(GPTPrompt prompt) {
        try {
            return webClient.post()
                    .uri(baseURL)
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(prompt)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> {
                        log.error("Chat GPT API failed to execute prompt {}.", prompt);
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new OpenAIException(
                                        String.format("Failed to execute prompt: %s. %s", prompt, errorBody), HttpStatusCode.valueOf(500))));
                    })
                    .bodyToMono(new ParameterizedTypeReference<ChatCompletionDTO>() {
                    })
                    .block();
        } catch (Exception e) {
            log.error("Error occurred while calling OpenAI API: {}", e.getMessage(), e);
            throw new OpenAIException("The OpenAI API request failed. Please try again later.", HttpStatusCode.valueOf(500));
        }
    }
}
