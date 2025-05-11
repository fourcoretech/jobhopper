package com.resume.analyzer.service;

import com.resume.common.library.service.OpenAIService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResumeScoringService {
    private final OpenAIService openAIService;

    public String analyzeResume(String resumeText) {
        String prompt = "Analyze the following resume and provide a score (0-10)" +
                " along with improvement suggestions:\n" + resumeText;
        return openAIService.callGPT(prompt);
    }
}
