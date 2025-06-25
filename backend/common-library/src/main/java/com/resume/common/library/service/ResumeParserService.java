package com.resume.common.library.service;

import com.resume.common.library.exception.ResumeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Service
@Slf4j
public class ResumeParserService {
    private final Tika tika = new Tika();

    public String extractText(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            Metadata metadata = createMetadata(file);

            String content = tika.parseToString(new ByteArrayInputStream(fileBytes), metadata);
            log.info("Extracted text length: {}", content != null ? content.length() : 0);

            return content;
        } catch (Exception e) {
            log.error("Error parsing file: {}", e.getMessage());
            throw new ResumeException("Error parsing file: " + e.getMessage(), HttpStatusCode.valueOf(500));
        }
    }

    private Metadata createMetadata(MultipartFile file) {
        Metadata metadata = new Metadata();
        metadata.set("resourceName", file.getOriginalFilename());
        metadata.set("Content-Type", file.getContentType());
        return metadata;
    }
}
