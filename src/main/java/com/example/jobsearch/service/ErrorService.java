package com.example.jobsearch.service;

import com.example.jobsearch.exceptions.ErrorResponceBody;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ErrorService {
    public ErrorResponceBody makeResponce(BindingResult bindingResult);
}
