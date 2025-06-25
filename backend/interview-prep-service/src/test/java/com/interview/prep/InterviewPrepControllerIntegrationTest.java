package com.interview.prep;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.prep.service.InterviewBotService;
import com.resume.common.library.service.ResumeParserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class InterviewPrepControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private InterviewBotService interviewBotService;

    @MockBean
    private ResumeParserService resumeParserService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testPrepareInterview_Success() throws Exception {
        MockMultipartFile resumeFile = new MockMultipartFile("file", "resume.pdf", MediaType.APPLICATION_PDF_VALUE, "Sample resume content".getBytes());
        String resumeText = "Extracted resume text";
        String interviewQuestions = "Sample interview questions";

        when(resumeParserService.extractText(resumeFile)).thenReturn(resumeText);
        when(interviewBotService.generateInterviewQuestions(resumeText)).thenReturn(interviewQuestions);

        mockMvc.perform(multipart("/resume/interview-prep")
                        .file(resumeFile))
                .andExpect(status().isOk())
                .andExpect(content().string(interviewQuestions));

        verify(resumeParserService, times(1)).extractText(resumeFile);
        verify(interviewBotService, times(1)).generateInterviewQuestions(resumeText);
    }

    @Test
    void testPrepareInterview_InvalidFileFormat() throws Exception {
        MockMultipartFile invalidFile = new MockMultipartFile("file", "resume.txt", MediaType.TEXT_PLAIN_VALUE, "Invalid file content".getBytes());
        mockMvc.perform(multipart("/resume/interview-prep")
                        .file(invalidFile))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(resumeParserService, interviewBotService);
    }

    @Test
    void testPrepareInterview_FileSizeExceedsLimit() throws Exception {
        byte[] largeFileContent = new byte[6 * 1024 * 1024];
        MockMultipartFile largeFile = new MockMultipartFile("file", "resume.pdf", MediaType.APPLICATION_PDF_VALUE, largeFileContent);
        mockMvc.perform(multipart("/resume/interview-prep")
                        .file(largeFile))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(resumeParserService, interviewBotService);
    }
}
