package com.interview.prep.controller;

import com.interview.prep.service.InterviewBotService;
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

import static com.resume.common.library.util.ResumeUtil.validateFile;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/interview")
public class InterviewPrepController {

    private final InterviewBotService interviewBotService;
    private final ResumeParserService resumeParserService;

    @PostMapping("/prepare")
    public ResponseEntity<?> prepareInterview(@RequestParam("file") MultipartFile resumeFile) throws IOException {
        validateFile(resumeFile);
        String resumeText = resumeParserService.extractText(resumeFile);
        String sanitizedResumeText = StringEscapeUtils.escapeHtml4(resumeText);
        String interviewQuestions = interviewBotService.generateInterviewQuestions(sanitizedResumeText);
        return ResponseEntity.ok(interviewQuestions);
    }
}
