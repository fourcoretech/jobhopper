package com.interview.prep.service;

import com.resume.common.library.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewBotService {

    private final OpenAIService openAIService;

    public String generateInterviewQuestions(String candidateResume) {
        String prompt = "Based on the following resume:\n" + candidateResume +
                "\nGenerate a list of 10 advanced interview questions with example answers. Then give advice for the candidate.";
        log.info(prompt);
        return openAIService.callGPT(prompt);
    }
}
