package com.example.jobsearch.vacancy.application.exception;

public class VacancyInUseException extends RuntimeException {
    public VacancyInUseException(Long id) {
        super("Vacancy with id " + id + " has responses and cannot be deleted");
    }
}
