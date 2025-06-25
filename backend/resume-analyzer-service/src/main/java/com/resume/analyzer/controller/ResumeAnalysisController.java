package com.resume.analyzer.controller;

import com.resume.analyzer.dto.ResumeAnalysisResponse;
import com.resume.analyzer.service.ProducerService;
import com.resume.analyzer.service.ResumeAnalysisService;
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
public class ResumeAnalysisController {

    private final ResumeParserService resumeParserService;
    private final ResumeExtractorService resumeExtractorService;
    private final ResumeAnalysisService resumeAnalysisService;
    private final ProducerService producerService;

    @PostMapping("/analyze")
    public ResponseEntity<ResumeAnalysisResponse> analyzeResume(@RequestParam("file") MultipartFile file) throws IOException {
        validateFile(file);
        String resumeText = resumeParserService.extractText(file);
        Map<String, String> extractedInfo = resumeExtractorService.extractInfo(resumeText);
        String scoringResult = resumeAnalysisService.analyzeResume(resumeText);
        producerService.sendNotificationEvent(NotificationEvent.builder()
                .eventType(EventTypes.RESUME_ANALYSIS)
                .recipientEmail(extractedInfo.get("email"))
                .summary(scoringResult)
                .build());
        return ResponseEntity.ok(new ResumeAnalysisResponse(scoringResult, extractedInfo.get("email")));
    }
}
