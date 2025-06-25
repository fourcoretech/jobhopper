package com.resume.analyzer;

import com.resume.analyzer.service.ResumeAnalysisService;
import com.resume.common.library.exception.OpenAIException;
import com.resume.common.library.service.OpenAIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ResumeAnalysisServiceTest {

    @Mock
    private OpenAIService openAIService;

    @InjectMocks
    private ResumeAnalysisService resumeAnalysisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resumeAnalysisService = new ResumeAnalysisService(openAIService);
    }

    @Test
    void testAnalyzeResume_Success() {
        String resumeText = "Sample resume text";
        String expectedResponse = "Score: 8, Suggestions: ...";

        when(openAIService.callGPT(anyString())).thenReturn(expectedResponse);

        String result = resumeAnalysisService.analyzeResume(resumeText);

        assertEquals(expectedResponse, result);
        verify(openAIService, times(1)).callGPT(anyString());
    }

    @Test
    void testAnalyzeResume_Exception() {
        String resumeText = "Sample resume text";

        when(openAIService.callGPT(anyString())).thenThrow(new OpenAIException("Service error", HttpStatusCode.valueOf(500)));

        assertThrows(OpenAIException.class, () ->
                resumeAnalysisService.analyzeResume(resumeText));
        verify(openAIService, times(1)).callGPT(anyString());
    }
}
