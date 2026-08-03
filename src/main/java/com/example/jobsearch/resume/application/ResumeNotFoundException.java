package com.example.jobsearch.resume.application;

public class ResumeNotFoundException extends RuntimeException {
    public ResumeNotFoundException(long id) { super("Resume " + id + " was not found"); }
}
