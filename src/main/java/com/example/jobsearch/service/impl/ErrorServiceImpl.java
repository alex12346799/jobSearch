package com.example.jobsearch.service.impl;

import com.example.jobsearch.exceptions.ErrorResponseBody;
import com.example.jobsearch.service.ErrorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ErrorServiceImpl implements  ErrorService {

    @Override
    public ErrorResponseBody makeResponse(BindingResult bindingResult){
        Map<String, List<String>> reasons = new HashMap<>();

        bindingResult.getFieldErrors().stream()
                .filter(e->e.getDefaultMessage() != null)
                .forEach(e->{

                    List<String> errors = new ArrayList<String>();
                    errors.add(e.getDefaultMessage());
                    if(!reasons.containsKey(e.getField())) {
                        reasons.put(e.getField(),errors);
                    }
                });
        return ErrorResponseBody.builder()
                .title("Validation errors")
                .response(reasons)
                .build();
    }
}
