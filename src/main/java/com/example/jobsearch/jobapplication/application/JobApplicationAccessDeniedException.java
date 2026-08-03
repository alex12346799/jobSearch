package com.example.jobsearch.jobapplication.application;

public class JobApplicationAccessDeniedException extends RuntimeException {
    public JobApplicationAccessDeniedException() {
        super("Access to this job application is forbidden");
    }
}
