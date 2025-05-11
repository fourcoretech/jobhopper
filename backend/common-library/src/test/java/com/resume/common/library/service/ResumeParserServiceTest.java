package com.resume.common.library.service;

import com.resume.common.library.exception.ResumeException;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResumeParserServiceTest {

    @InjectMocks
    private ResumeParserService resumeParserService;

    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        mockFile = mock(MultipartFile.class);
    }

    @Test
    void testExtractText_SuccessfulExtraction() throws IOException, TikaException {
        when(mockFile.getBytes()).thenReturn("Sample content".getBytes(StandardCharsets.UTF_8));
        when(mockFile.getOriginalFilename()).thenReturn("resume.pdf");
        when(mockFile.getContentType()).thenReturn("application/pdf");

        String extractedText = resumeParserService.extractText(mockFile);

        assertNotNull(extractedText);
        assertTrue(extractedText.contains("Sample content"));
        verify(mockFile, times(1)).getBytes();
    }

    @Test
    void testExtractText_EmptyFile() throws IOException {
        when(mockFile.getBytes()).thenReturn(new byte[0]);
        when(mockFile.getOriginalFilename()).thenReturn("empty.pdf");
        when(mockFile.getContentType()).thenReturn("application/pdf");
        assertThrows(ResumeException.class, () -> resumeParserService.extractText(mockFile));
    }

    @Test
    void testExtractText_TikaException() throws IOException {
        doThrow(new ResumeException("Error parsing file: null", HttpStatusCode.valueOf(500)))
                .when(mockFile).getBytes();
        ResumeException exception = assertThrows(ResumeException.class, () -> resumeParserService.extractText(mockFile));
        assertEquals("Error parsing file: null", exception.getErrorMessage());
        assertEquals(500, exception.getStatusCode().value());
    }
}
