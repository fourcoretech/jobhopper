package com.resume.analyzer.service;

import com.resume.common.library.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobMatchingService {

    private final OpenAIService openAIService;

    public String matchJob(String resumeText, String jobDescription) {
        String prompt = "Compare this resume to the following job description. " +
                "Give a compatibility score from 0 to 100 based on required skills, experience, and keywords. " +
                "List strengths, weaknesses, and ways to improve alignment.";
        return openAIService.callGPT(prompt);
    }
}

