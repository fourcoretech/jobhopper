package com.resume.analyzer.service;

import com.resume.common.library.service.OpenAIService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResumeAnalysisService {
    private final OpenAIService openAIService;

    public String analyzeResume(String resumeText) {
        String prompt = "Evaluate this resume for professionalism, clarity, structure, and relevance to modern job applications. " +
                "Provide a score from 0 to 100 and a list of improvement suggestions\n" + resumeText;
        return openAIService.callGPT(prompt);
    }
}
