package com.resume.analyzer;

import com.resume.analyzer.service.JobMatchingService;
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
class JobMatchingControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private JobMatchingService jobMatchingService;

    @MockBean
    private ResumeParserService resumeParserService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testMatchJob_Success() throws Exception {
        MockMultipartFile resumeFile = new MockMultipartFile("file", "resume.pdf", MediaType.APPLICATION_PDF_VALUE, "Sample resume content".getBytes());
        String jobDescription = "Sample job description";
        String resumeText = "Extracted resume text";
        String matchResult = "Match score: 85, Recommendations: ...";

        when(resumeParserService.extractText(resumeFile)).thenReturn(resumeText);
        when(jobMatchingService.matchJob(resumeText, jobDescription)).thenReturn(matchResult);
        mockMvc.perform(multipart("/job-match/match")
                        .file(resumeFile)
                        .param("jobDescription", jobDescription))
                .andExpect(status().isOk())
                .andExpect(content().string(matchResult));
        verify(resumeParserService, times(1)).extractText(resumeFile);
        verify(jobMatchingService, times(1)).matchJob(resumeText, jobDescription);
    }

    @Test
    void testMatchJob_InvalidFile() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile("file", null, MediaType.APPLICATION_PDF_VALUE, new byte[0]);
        mockMvc.perform(multipart("/job-match/match")
                        .file(emptyFile)
                        .param("jobDescription", "Sample job description"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(jobMatchingService);
    }
}
