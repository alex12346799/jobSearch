package com.example.jobsearch.exceptions;

public class ApplicantNotFoundException extends NotFoundException {
    public ApplicantNotFoundException() {
        super("applicant not found");
    }
}
