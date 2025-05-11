package com.resume.common.library.service;

import com.resume.common.library.exception.ResumeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ResumeExtractorServiceTest {

    private ResumeExtractorService resumeExtractorService;

    @BeforeEach
    void setUp() {
        resumeExtractorService = new ResumeExtractorService();
    }

    @Test
    void testExtractInfo_SuccessfulExtraction() {
        String resumeText = "John Doe\njohn.doe@example.com\n+1234567890";

        Map<String, String> extractedData = resumeExtractorService.extractInfo(resumeText);

        assertEquals("John Doe", extractedData.get("name"));
        assertEquals("john.doe@example.com", extractedData.get("email"));
        assertEquals("+1234567890", extractedData.get("phone"));
    }

    @Test
    void testExtractInfo_MissingEmail() {
        String resumeText = "John Doe\n+1234567890";

        Map<String, String> extractedData = resumeExtractorService.extractInfo(resumeText);

        assertEquals("John Doe", extractedData.get("name"));
        assertNull(extractedData.get("email"));
        assertEquals("+1234567890", extractedData.get("phone"));
    }

    @Test
    void testExtractInfo_MissingPhone() {
        String resumeText = "John Doe\njohn.doe@example.com";

        Map<String, String> extractedData = resumeExtractorService.extractInfo(resumeText);

        assertEquals("John Doe", extractedData.get("name"));
        assertEquals("john.doe@example.com", extractedData.get("email"));
        assertNull(extractedData.get("phone"));
    }

    @Test
    void testExtractInfo_NullInput() {
        assertThrows(ResumeException.class, () -> resumeExtractorService.extractInfo(null));
    }
}
