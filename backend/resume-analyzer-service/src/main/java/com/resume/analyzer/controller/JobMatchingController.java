package com.resume.analyzer.controller;

import com.resume.analyzer.dto.JobMatchingResponse;
import com.resume.analyzer.service.JobMatchingService;
import com.resume.analyzer.service.ProducerService;
import com.resume.common.library.model.notify.dto.NotificationEvent;
import com.resume.common.library.model.notify.enumerations.EventTypes;
import com.resume.common.library.service.ResumeExtractorService;
import com.resume.common.library.service.ResumeParserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class JobMatchingController {

    private final JobMatchingService jobMatchingService;
    private final ResumeParserService resumeParserService;
    private final ResumeExtractorService resumeExtractorService;
    private final ProducerService producerService;

    @PostMapping("/job-match")
    public ResponseEntity<JobMatchingResponse> matchJob(@RequestParam("file") MultipartFile resumeFile,
                                           @RequestParam("jobDescription") String jobDescription) throws IOException {
        validateFile(resumeFile);
        String resumeText = resumeParserService.extractText(resumeFile);
        Map<String, String> extractedInfo = resumeExtractorService.extractInfo(resumeText);
        String matchResult = jobMatchingService.matchJob(resumeText, jobDescription);
        producerService.sendNotificationEvent(NotificationEvent.builder()
                .eventType(EventTypes.JOB_MATCH)
                .recipientEmail(extractedInfo.get("email"))
                .summary(matchResult)
                .build());
        return ResponseEntity.ok(new JobMatchingResponse(matchResult, extractedInfo.get("email")));
    }
}
