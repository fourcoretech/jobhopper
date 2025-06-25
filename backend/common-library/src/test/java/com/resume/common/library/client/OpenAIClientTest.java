package com.resume.common.library.client;

import com.resume.common.library.model.openai.dto.ChatCompletionDTO;
import com.resume.common.library.model.openai.dto.GPTPrompt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OpenAIClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @InjectMocks
    private OpenAIClient openAIClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        openAIClient = new OpenAIClient(webClient);
        ReflectionTestUtils.setField(openAIClient, "baseURL", "https://api.openai.com/v1/chat/completions");
        ReflectionTestUtils.setField(openAIClient, "apiKey", "test-api-key");
    }

    @Test
    void testPrompt_SuccessfulResponse() {
        GPTPrompt prompt = new GPTPrompt();
        ChatCompletionDTO expectedResponse = new ChatCompletionDTO();
        doReturn(requestBodyUriSpec).when(webClient).post();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(anyString());
        doReturn(requestBodySpec).when(requestBodySpec).header(anyString(), anyString());
        doReturn(requestBodySpec).when(requestBodySpec).bodyValue(prompt);
        doReturn(responseSpec).when(requestBodySpec).retrieve();
        doReturn(responseSpec).when(responseSpec).onStatus(any(), any());
        doReturn(Mono.just(expectedResponse)).when(responseSpec).bodyToMono(new ParameterizedTypeReference<ChatCompletionDTO>() {
        });

        ChatCompletionDTO actualResponse = openAIClient.prompt(prompt);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(webClient, times(1)).post();
    }

    @Test
    void testPrompt_ApiErrorResponse() {
        GPTPrompt prompt = new GPTPrompt();
        doReturn(requestBodyUriSpec).when(webClient).post();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(anyString());
        doReturn(requestBodySpec).when(requestBodySpec).bodyValue(prompt);
        doReturn(responseSpec).when(requestBodySpec).retrieve();
        doAnswer(invocation -> {
            throw new RuntimeException("API error");
        }).when(responseSpec).onStatus(any(), any());
        assertThrows(RuntimeException.class, () -> openAIClient.prompt(prompt));
    }

    @Test
    void testPrompt_ExceptionHandling() {
        GPTPrompt prompt = new GPTPrompt();
        doReturn(requestBodyUriSpec).when(webClient).post();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(anyString());
        doReturn(requestBodySpec).when(requestBodySpec).bodyValue(prompt);
        doReturn(responseSpec).when(requestBodySpec).retrieve();
        when(requestHeadersSpec.retrieve()).thenThrow(new RuntimeException("Network error"));
        assertThrows(RuntimeException.class, () -> openAIClient.prompt(prompt));
        verify(webClient, times(1)).post();
    }
}
