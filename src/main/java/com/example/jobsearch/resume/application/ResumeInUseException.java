package com.example.jobsearch.resume.application;

public class ResumeInUseException extends RuntimeException {
    public ResumeInUseException() { super("Resume cannot be deleted because it has related applications"); }
}
