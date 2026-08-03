package com.example.jobsearch.auth.web;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public interface StrictAuthRequest {
    @JsonAnySetter
    default void rejectUnknownField(String field, Object value) {
        throw new IllegalArgumentException("Unknown authentication request field: " + field);
    }
}
