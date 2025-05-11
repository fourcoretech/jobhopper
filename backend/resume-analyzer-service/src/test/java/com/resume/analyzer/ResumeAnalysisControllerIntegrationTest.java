package com.resume.analyzer;

import com.resume.analyzer.service.ResumeScoringService;
import com.resume.common.library.service.ResumeExtractorService;
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

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ResumeAnalysisControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ResumeParserService resumeParserService;

    @MockBean
    private ResumeExtractorService resumeExtractorService;

    @MockBean
    private ResumeScoringService resumeScoringService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testAnalyzeResume_Success() throws Exception {
        MockMultipartFile resumeFile = new MockMultipartFile("file", "resume.pdf", MediaType.APPLICATION_PDF_VALUE, "Sample resume content".getBytes());
        String resumeText = "Extracted resume text";
        Map<String, String> extractedInfo = Map.of("Name", "John Doe", "Email", "john.doe@example.com");
        String scoringResult = "Score: 8, Suggestions: ...";

        when(resumeParserService.extractText(resumeFile)).thenReturn(resumeText);
        when(resumeExtractorService.extractInfo(resumeText)).thenReturn(extractedInfo);
        when(resumeScoringService.analyzeResume(resumeText)).thenReturn(scoringResult);

        mockMvc.perform(multipart("/analysis/analyze")
                        .file(resumeFile))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"extractedInfo\":{\"Name\":\"John Doe\",\"Email\":\"john.doe@example.com\"},\"scoringResult\":\"Score: 8, Suggestions: ...\"}"));

        verify(resumeParserService, times(1)).extractText(resumeFile);
        verify(resumeExtractorService, times(1)).extractInfo(resumeText);
        verify(resumeScoringService, times(1)).analyzeResume(resumeText);
    }

    @Test
    void testAnalyzeResume_NoFileUploaded() throws Exception {
        mockMvc.perform(multipart("/analysis/analyze"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(resumeParserService, resumeExtractorService, resumeScoringService);
    }

    @Test
    void testAnalyzeResume_EmptyFile() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile("file", null, MediaType.APPLICATION_PDF_VALUE, new byte[0]);
        mockMvc.perform(multipart("/analysis/analyze")
                .file(emptyFile)).andExpect(status().isBadRequest());
        verifyNoInteractions(resumeExtractorService, resumeScoringService);
    }
}
