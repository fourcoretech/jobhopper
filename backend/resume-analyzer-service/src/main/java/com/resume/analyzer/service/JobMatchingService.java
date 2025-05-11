package com.resume.analyzer.service;

import com.resume.common.library.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobMatchingService {

    private final OpenAIService openAIService;

    public String matchJob(String resumeText, String jobDescription) {
        String prompt = "Given the following resume:\n" + resumeText +
                "\nAnd the following job description:\n" + jobDescription +
                "\nPlease provide a match score (0-100) and " +
                "recommendations to improve the resume's fit " +
                "along with recommended topics to study.";
        return openAIService.callGPT(prompt);
    }
}

