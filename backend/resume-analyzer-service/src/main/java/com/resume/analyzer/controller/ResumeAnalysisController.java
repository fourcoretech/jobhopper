package com.resume.analyzer.controller;

import com.resume.analyzer.service.ResumeScoringService;
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
@RequestMapping("/analysis")
public class ResumeAnalysisController {

    private final ResumeParserService resumeParserService;
    private final ResumeExtractorService resumeExtractorService;
    private final ResumeScoringService resumeScoringService;

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeResume(@RequestParam("file") MultipartFile file) throws IOException {
        validateFile(file);
        String resumeText = resumeParserService.extractText(file);
        Map<String, String> extractedInfo = resumeExtractorService.extractInfo(resumeText);
        String scoringResult = resumeScoringService.analyzeResume(resumeText);
        return ResponseEntity.ok(Map.of(
                "extractedInfo", extractedInfo,
                "scoringResult", scoringResult
        ));
    }
}
