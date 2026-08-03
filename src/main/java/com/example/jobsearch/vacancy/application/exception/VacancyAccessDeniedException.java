package com.example.jobsearch.vacancy.application.exception;

public class VacancyAccessDeniedException extends RuntimeException {
    public VacancyAccessDeniedException(String message) {
        super(message);
    }
}
