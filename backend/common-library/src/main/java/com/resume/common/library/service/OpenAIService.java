package com.resume.common.library.service;


import com.resume.common.library.client.OpenAIClient;
import com.resume.common.library.exception.OpenAIException;
import com.resume.common.library.model.openai.dto.ChatCompletionDTO;
import com.resume.common.library.model.openai.dto.GPTPrompt;
import com.resume.common.library.model.openai.dto.Messages;
import com.resume.common.library.model.openai.enumerations.GPTModels;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OpenAIService {
    private final OpenAIClient openAIClient;

    public String callGPT(String prompt) {
        try {
            ChatCompletionDTO gptResponse = openAIClient.prompt(GPTPrompt.builder()
                    .model(GPTModels.GPT_4O.getModelName())
                    .messages(List.of(Messages.builder()
                            .role("user")
                            .content(prompt)
                            .build()))
                    .build());
            return gptResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            log.error("Error calling OpenAI API: {}", e.getMessage());
            throw new OpenAIException(String.format("Failed to call OpenAI API. %s", e.getMessage()), HttpStatusCode.valueOf(500));
        }
    }
}
