package com.example.jobsearch.resume.application;

public class ResumeAccessDeniedException extends RuntimeException {
    public ResumeAccessDeniedException() { super("You are not allowed to modify this resume"); }
}
