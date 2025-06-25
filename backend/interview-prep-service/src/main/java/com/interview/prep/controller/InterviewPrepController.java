package com.interview.prep.controller;

import com.interview.prep.dto.InterviewPrepResponse;
import com.interview.prep.service.InterviewBotService;
import com.interview.prep.service.ProducerService;
import com.resume.common.library.model.notify.dto.NotificationEvent;
import com.resume.common.library.model.notify.enumerations.EventTypes;
import com.resume.common.library.service.ResumeExtractorService;
import com.resume.common.library.service.ResumeParserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static com.resume.common.library.util.ResumeUtil.validateFile;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/resume")
public class InterviewPrepController {

    private final InterviewBotService interviewBotService;
    private final ResumeParserService resumeParserService;
    private final ResumeExtractorService resumeExtractorService;
    private final ProducerService producerService;

    @PostMapping("/interview-prep")
    public ResponseEntity<InterviewPrepResponse> prepareInterview(@RequestParam("role") String role, @RequestParam("jobDescription") String jobDescription, @RequestParam("file") MultipartFile resumeFile) throws IOException {
        validateFile(resumeFile);
        String resumeText = resumeParserService.extractText(resumeFile);
        String sanitizedResumeText = StringEscapeUtils.escapeHtml4(resumeText);
        String interviewQuestions = interviewBotService.generateInterviewQuestions(role, jobDescription, sanitizedResumeText);
        Map<String, String> extractedInfo = resumeExtractorService.extractInfo(resumeText);
        producerService.sendNotificationEvent(NotificationEvent.builder()
                .eventType(EventTypes.INTERVIEW_PREP)
                .recipientEmail(extractedInfo.get("email"))
                .summary(interviewQuestions)
                .build());
        return ResponseEntity.ok(new InterviewPrepResponse(interviewQuestions, extractedInfo.get("email")));
    }
}
