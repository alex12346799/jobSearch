package com.example.jobsearch.resume.web;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public interface StrictResumeRequest {
    @JsonAnySetter
    default void rejectUnknownField(String field, Object value) {
        throw new IllegalArgumentException("Unknown resume request field: " + field);
    }
}
