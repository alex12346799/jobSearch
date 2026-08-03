package com.example.jobsearch.jobapplication.web;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public interface StrictJobApplicationRequest {
    @JsonAnySetter
    default void rejectUnknownField(String field, Object value) {
        throw new IllegalArgumentException("Unknown job application request field: " + field);
    }
}
