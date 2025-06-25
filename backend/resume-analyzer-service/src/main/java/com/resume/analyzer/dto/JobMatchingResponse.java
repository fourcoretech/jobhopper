package com.resume.analyzer.dto;

public class JobMatchingResponse {
    private String matchResult;
    private String email;

    public JobMatchingResponse(String matchResult, String email) {
        this.matchResult = matchResult;
        this.email = email;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
