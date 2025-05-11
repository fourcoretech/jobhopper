package com.resume.analyzer.controller;

import com.resume.analyzer.service.JobMatchingService;
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

import static com.resume.common.library.util.ResumeUtil.validateFile;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/job-match")
public class JobMatchingController {

    private final JobMatchingService jobMatchingService;
    private final ResumeParserService resumeParserService;

    @PostMapping("/match")
    public ResponseEntity<?> matchJob(@RequestParam("file") MultipartFile resumeFile,
                                      @RequestParam("jobDescription") String jobDescription) throws IOException {
        validateFile(resumeFile);
        String resumeText = resumeParserService.extractText(resumeFile);
        String matchResult = jobMatchingService.matchJob(resumeText, jobDescription);
        return ResponseEntity.ok(matchResult);
    }
}
