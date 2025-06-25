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

    public String generateInterviewQuestions(String role, String jobDescription, String candidateResume) {
        String prompt = String.format(
                """
                        Using the following data:       
                        • Resume:
                        %s      
                        • Target Job Role:
                        %s          
                        • Job Description:
                        %s          
                        ---
                        Task:
                        Generate 5–7 interview questions **with ideal sample answers** tailored to the candidate’s resume and the target role.  
                        • Mix behavioral, technical, and situational questions.  
                        • Highlight any skill gaps or recommendations if the resume does not fully match the job requirements.  
                        """,
                candidateResume,
                role,
                jobDescription
        );
        log.info(prompt);
        return openAIService.callGPT(prompt);
    }
}
