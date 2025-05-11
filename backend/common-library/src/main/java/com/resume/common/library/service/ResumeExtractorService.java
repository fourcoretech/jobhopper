package com.resume.common.library.service;


import com.resume.common.library.exception.ResumeException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeExtractorService {

    public Map<String, String> extractInfo(String resumeText) {
        try {
            Map<String, String> extractedData = new HashMap<>();
            // Assume first non-empty line is the candidate's name
            String[] lines = resumeText.split("\n");
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    extractedData.put("name", line.trim());
                    break;
                }
            }

            // Extract Email
            Matcher emailMatcher = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")
                    .matcher(resumeText);
            if (emailMatcher.find()) {
                extractedData.put("email", emailMatcher.group());
            }

            // Extract Phone
            Matcher phoneMatcher = Pattern.compile("\\+?\\d{10,13}").matcher(resumeText);
            if (phoneMatcher.find()) {
                extractedData.put("phone", phoneMatcher.group());
            }

            return extractedData;
        } catch (Exception e) {
            throw new ResumeException(
                    "Error extracting information from resume: " + e.getMessage(),
                    HttpStatusCode.valueOf(500));
        }
    }
}
