package com.example.jobsearch.service;


import com.example.jobsearch.exceptions.ErrorResponseBody;
import org.springframework.validation.BindingResult;


public interface ErrorService {
    public ErrorResponseBody makeResponse(BindingResult bindingResult);
}
