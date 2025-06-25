package com.resume.analyzer.dto;

public class ResumeAnalysisResponse {
    private String scoringResult;
    private String email;

    public ResumeAnalysisResponse(String scoringResult, String email) {
        this.scoringResult = scoringResult;
        this.email = email;
    }

    public String getScoringResult() {
        return scoringResult;
    }

    public void setScoringResult(String scoringResult) {
        this.scoringResult = scoringResult;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
