package com.resume.analyzer;

import com.resume.analyzer.service.JobMatchingService;
import com.resume.common.library.exception.OpenAIException;
import com.resume.common.library.service.OpenAIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class JobMatchingServiceTest {

    @Mock
    private OpenAIService openAIService;

    private JobMatchingService jobMatchingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jobMatchingService = new JobMatchingService(openAIService);
    }

    @Test
    void testMatchJob_Success() {
        String resumeText = "Sample resume text";
        String jobDescription = "Sample job description";
        String expectedResponse = "Match score: 85, Recommendations: ...";

        when(openAIService.callGPT(anyString())).thenReturn(expectedResponse);

        String result = jobMatchingService.matchJob(resumeText, jobDescription);

        assertEquals(expectedResponse, result);
        verify(openAIService, times(1)).callGPT(anyString());
    }

    @Test
    void testMatchJob_Exception() {
        String resumeText = "Sample resume text";
        String jobDescription = "Sample job description";
        when(openAIService.callGPT(anyString())).thenThrow(new OpenAIException("Service error", HttpStatusCode.valueOf(500)));
        assertThrows(OpenAIException.class, () ->
                jobMatchingService.matchJob(resumeText, jobDescription));
        verify(openAIService, times(1)).callGPT(anyString());
    }
}
