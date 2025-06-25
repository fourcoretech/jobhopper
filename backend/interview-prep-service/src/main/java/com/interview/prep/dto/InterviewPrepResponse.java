package com.interview.prep.dto;

public class InterviewPrepResponse {
    private String interviewQuestions;
    private String email;

    public InterviewPrepResponse(String interviewQuestions, String email) {
        this.interviewQuestions = interviewQuestions;
        this.email = email;
    }

    public String getInterviewQuestions() {
        return interviewQuestions;
    }

    public void setInterviewQuestions(String interviewQuestions) {
        this.interviewQuestions = interviewQuestions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
