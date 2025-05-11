package com.interview.prep;

import com.interview.prep.exception.InterviewPrepException;
import com.interview.prep.service.InterviewBotService;
import com.resume.common.library.service.OpenAIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class InterviewBotServiceTest {

    @Mock
    private OpenAIService openAIService;

    private InterviewBotService interviewBotService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        interviewBotService = new InterviewBotService(openAIService);
    }

    @Test
    void testGenerateInterviewQuestions_Success() {
        String candidateResume = "Sample resume text";
        String expectedResponse = "List of interview questions and advice";

        when(openAIService.callGPT(anyString())).thenReturn(expectedResponse);

        String result = interviewBotService.generateInterviewQuestions(candidateResume);

        assertEquals(expectedResponse, result);
        verify(openAIService, times(1)).callGPT(anyString());
    }

    @Test
    void testGenerateInterviewQuestions_ClientError() {
        String candidateResume = "Sample resume text";

        when(openAIService.callGPT(anyString()))
                .thenThrow(new InterviewPrepException(HttpStatus.BAD_REQUEST, "Client error"));

        assertThrows(InterviewPrepException.class, () ->
                interviewBotService.generateInterviewQuestions(candidateResume));
        verify(openAIService, times(1)).callGPT(anyString());
    }

    @Test
    void testGenerateInterviewQuestions_ServerError() {
        String candidateResume = "Sample resume text";

        when(openAIService.callGPT(anyString()))
                .thenThrow(new InterviewPrepException(HttpStatus.SERVICE_UNAVAILABLE, "Server error"));

        assertThrows(InterviewPrepException.class, () ->
                interviewBotService.generateInterviewQuestions(candidateResume));
        verify(openAIService, times(1)).callGPT(anyString());
    }

    @Test
    void testGenerateInterviewQuestions_GenericError() {
        String candidateResume = "Sample resume text";

        when(openAIService.callGPT(anyString()))
                .thenThrow(new InterviewPrepException("Unexpected error", HttpStatusCode.valueOf(500)));

        assertThrows(InterviewPrepException.class, () ->
                interviewBotService.generateInterviewQuestions(candidateResume));
        verify(openAIService, times(1)).callGPT(anyString());
    }
}
