package com.resume.common.library.service;

import com.resume.common.library.client.OpenAIClient;
import com.resume.common.library.model.openai.dto.ChatCompletionDTO;
import com.resume.common.library.model.openai.dto.GPTPrompt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpenAIServiceTest {

    @Mock
    private OpenAIClient openAIClient;

    @InjectMocks
    private OpenAIService openAIService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPrompt_SuccessfulResponse() {
        ChatCompletionDTO expectedResponse = new ChatCompletionDTO();
        ChatCompletionDTO.Choice choice = new ChatCompletionDTO.Choice();
        ChatCompletionDTO.Messages messages = new ChatCompletionDTO.Messages();
        messages.setContent("response");
        choice.setMessage(messages);
        expectedResponse.setChoices(List.of(choice));

        when(openAIClient.prompt(any(GPTPrompt.class))).thenReturn(expectedResponse);

        String actualResponse = openAIService.callGPT("prompt");

        assertNotNull(actualResponse);
        assertEquals("response", actualResponse);
        verify(openAIClient, times(1)).prompt(any(GPTPrompt.class));
    }

    @Test
    void testPrompt_ExceptionHandling() {
        when(openAIClient.prompt(any(GPTPrompt.class))).thenThrow(new RuntimeException("API error"));
        assertThrows(RuntimeException.class, () -> openAIService.callGPT("prompt"));
        verify(openAIClient, times(1)).prompt(any(GPTPrompt.class));
    }
}
