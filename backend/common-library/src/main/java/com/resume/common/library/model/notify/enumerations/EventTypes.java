package com.resume.common.library.model.notify.enumerations;

public enum EventTypes {
    USER_LOGIN("Login Successful!"),
    NEW_USER("New User Created!"),
    FAILED_LOGIN("Failed Login Attempt!"),
    RESUME_ANALYSIS("Resume Analysis Results!"),
    JOB_MATCH("Job Matching Results!"),
    INTERVIEW_PREP("Interview Prep Results!"),
    TEST_EVENT("Test Event for Notification System!");

    private final String eventType;

    EventTypes(String eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return eventType;
    }
}
